package ui.donation;

import com.compass.common.Response;
import com.compass.donation.DonationController;
import com.compass.donation.dtos.CreateDonationResponseDto;
import com.compass.donation.dtos.DonationDto;

import com.compass.item.dtos.ItemDto;
import com.compass.item.enums.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ui.Component;
import ui.exceptions.OperationCancelledException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class RegisterDonationByFileUI {
    private final DonationController donationController;
    private final Component component;

    public RegisterDonationByFileUI(DonationController donationController, Component component) {
        this.donationController = donationController;
        this.component = component;
    }

    public void execute() {
        try {
            String label = "Informe o caminho para o arquivo.";
            String textInfo = "Certifique-se de que o arquivo CSV esteja no formato correto";
            File file = component.fileField(label, textInfo, "csv");
            List<ItemDto> items = listItems(file);

            Map<Long, List<ItemDto>> itemsByCenter = items.stream().collect(Collectors.groupingBy(ItemDto::centerId));
            List<DonationDto> donations = new ArrayList<>();

            for (Map.Entry<Long, List<ItemDto>> entry : itemsByCenter.entrySet()) {
                Long centerId = entry.getKey();
                List<ItemDto> itemsForCenter = entry.getValue();

                DonationDto donation = new DonationDto(LocalDate.now(), centerId, itemsForCenter);
                donations.add(donation);
            }

            Response<List<CreateDonationResponseDto>> response = donationController.saveMany(donations);
            if(response.getData() == null) {
                System.out.println();
                System.out.println(response.getMessage());
            }
            else {
                System.out.println();
                System.out.println(response.getMessage());
                response.getData().forEach(item -> {
                    System.out.println("Doação ID: " + item.donationId());
                    System.out.println("Centro: " + item.centerName());
                    System.out.println("Quantidade de itens: " + item.quantity());
                    System.out.println();
                });
            }
        }
        catch (FileNotFoundException exception) {
            System.out.println("Não foi possível iniciar a leitura do arquivo csv selecionado!");
        }
        catch (IOException | CsvValidationException exception)  {
            System.out.println("Erro durante a leitura do arquivo csv selecionado!");
        }
        catch (OperationCancelledException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public List<ItemDto> listItems(File file) throws FileNotFoundException, IOException, CsvValidationException, OperationCancelledException {
        FileReader fileReader = new FileReader(file);
        CSVReader csvReader = new CSVReader(fileReader);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        List<ItemDto> records = new ArrayList<>();
        String[] recordTemp = null;

        Map<Integer, String[]> recordsInvalid = new HashMap<>();
        Integer line = 0;

        while((recordTemp = csvReader.readNext()) != null) {
            try {
                line++;

                Long centerID = Long.parseLong(recordTemp[0]);
                CategoryItem category = CategoryItem.valueOf(recordTemp[1]);
                String description = recordTemp[2];
                SizeItem size =  null;
                GenderItem gender =  null;
                LocalDate expirationDate = null;
                Integer quantity = null;
                UnitItem unit = null;
                HygieneTypeItem hygieneType = null;

                if(category.equals(CategoryItem.ROUPA)) {
                    size = SizeItem.valueOf(recordTemp[4]);
                    gender = GenderItem.valueOf(recordTemp[5]);
                }

                if(category.equals(CategoryItem.ALIMENTO)) {
                    expirationDate = LocalDate.parse(recordTemp[6], dateFormatter);
                    quantity = Integer.parseInt(recordTemp[3]);
                    unit = UnitItem.valueOf(recordTemp[7]);
                }

                if(category.equals(CategoryItem.HIGIENE)) {
                    hygieneType = HygieneTypeItem.valueOf(recordTemp[8]);
                }

                ItemDto item = new ItemDto(null, description, quantity, category, centerID, size, gender, expirationDate, unit, hygieneType);
                records.add(item);
            }
            catch (IllegalArgumentException exception) {
                recordsInvalid.put(line, recordTemp);
            }
        }

        if(recordsInvalid.size() > 0) printInvalidRecords(recordsInvalid);

        return records;
    }

    public void printInvalidRecords(Map<Integer, String[]> recordsInvalid) {
        System.out.println();
        System.out.println("Registros inválidos:");
        for (Map.Entry<Integer, String[]> entry : recordsInvalid.entrySet()) {
            Integer line = entry.getKey();
            String[] record = entry.getValue();
            System.out.println("Linha " + line + ": " + Arrays.toString(record));
        }

        Boolean confirmation = component.confirmation("Deseja continuar a operação? Caso continue, os registros inválidos serão ignorados.");
        if (!confirmation) throw new OperationCancelledException("Operação cancelada pelo usuário!");
    }
}
