package ui;

import com.compass.center.CenterController;
import com.compass.donation.DonationController;
import com.compass.donation.dtos.DonationDto;

import com.compass.item.dtos.ItemDto;
import com.compass.item.enums.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ui.exceptions.OperationCancelledException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static ui.Component.fileField;

public class RegisterDonationByFileUI {
    private final CenterController centerController;
    private final DonationController donationController;
    private final Scanner scanner;

    public RegisterDonationByFileUI(CenterController centerController, DonationController donationController, Scanner scanner) {
        this.centerController = centerController;
        this.donationController = donationController;
        this.scanner = scanner;
    }

    public void execute() {
        try {
            String label = "Informe o caminho para o arquivo.";
            String textInfo = "Certifique-se de que o arquivo CSV esteja no formato correto";
            File file = fileField(scanner, label, textInfo, "csv");
            List<ItemDto> items = listItems(file);

            Map<Long, List<ItemDto>> itemsByCenter = items.stream().collect(Collectors.groupingBy(ItemDto::centerId));
            List<DonationDto> donations = new ArrayList<>();

            for (Map.Entry<Long, List<ItemDto>> entry : itemsByCenter.entrySet()) {
                Long centerId = entry.getKey();
                List<ItemDto> itemsForCenter = entry.getValue();

                DonationDto donation = new DonationDto(LocalDate.now(), centerId, itemsForCenter);
                donations.add(donation);
            }

            donationController.saveMany(donations);
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

    public List<ItemDto> listItems(File file) throws FileNotFoundException, IOException, CsvValidationException {
        FileReader fileReader = new FileReader(file);
        CSVReader csvReader = new CSVReader(fileReader);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        List<ItemDto> records = new ArrayList<>();
        String[] recordTemp = null;

        while((recordTemp = csvReader.readNext()) != null) {
            Long centerID = Long.parseLong(recordTemp[0]);
            CategoryItem category = CategoryItem.valueOf(recordTemp[1]);
            String description = recordTemp[2];
            Integer quantity = Integer.parseInt(recordTemp[3]);
            SizeItem size =  null;
            GenderItem gender =  null;
            LocalDate expirationDate = null;
            UnitItem unit = null;
            HygieneTypeItem hygieneType = null;

            if(category.equals(CategoryItem.ROUPA)) {
                size = SizeItem.valueOf(recordTemp[4]);
                gender = GenderItem.valueOf(recordTemp[5]);
            }

            if(category.equals(CategoryItem.ALIMENTO)) {
                expirationDate = LocalDate.parse(recordTemp[6], dateFormatter);
                unit = UnitItem.valueOf(recordTemp[7]);
            }

            if(category.equals(CategoryItem.HIGIENE)) {
                hygieneType = HygieneTypeItem.valueOf(recordTemp[8]);
            }

            ItemDto item = new ItemDto(description, quantity, category, centerID, size, gender, expirationDate, unit, hygieneType);
            records.add(item);
        }
        return records;
    }
}
