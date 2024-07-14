package controllers.donation;

import com.compass.center.CenterEntity;
import com.compass.center.CenterService;
import com.compass.donation.DonationController;
import com.compass.donation.DonationEntity;
import com.compass.donation.DonationService;
import com.compass.item.ItemEntity;
import com.compass.item.enums.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ui.Component;
import ui.exceptions.OperationCancelledException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RegisterDonationByFile {
    private final CenterService centerService;
    private final DonationController donationController;
    private final Component component;

    public RegisterDonationByFile(CenterService centerService, DonationController donationController, Component component) {
        this.centerService = centerService;
        this.donationController = donationController;
        this.component = component;
    }

    public void execute() {
        while (true) {
            try {
                String label = "Digite o caminho do arquivo CSV: ";
                String textInfo = "Certifique-se de que o arquivo CSV esteja no formato correto";
                String filePath = component.stringField(label, textInfo, null, null, null, null);
                System.out.println("Arquivo CSV: " + filePath);

                DonationEntity donation = new DonationEntity();
                donation.setDate(LocalDate.now());
                donation.setItems(new ArrayList<>());

                List<List<String>> records = readCSVFile(filePath);

                for (List<String> record : records) {
                    try {
                        Long centerId = Long.parseLong(record.get(0));
                        String category = record.get(1);
                        String description = record.get(2);
                        Integer quantity = Integer.parseInt(record.get(3));

                        CenterEntity center = centerService.findById(centerId);

                        donation.setCenter(center);

                        ItemEntity item = new ItemEntity();
                        item.setCenter(center);
                        item.setDescription(description);
                        item.setQuantity(quantity);

                        if(category == "ROUPA") {
                            SizeItem size = SizeItem.valueOf(record.get(4));
                            GenderItem genderItem = GenderItem.valueOf(record.get(5));

                            item.setCategory(CategoryItem.ROUPA);
                            item.setSize(size);
                            item.setGender(genderItem);

                            donation.addItem(item);
                        }

                        if(category == "ALIMENTO") {
                            LocalDate expirationDate = LocalDate.parse(record.get(4), DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH));
                            UnitItem unit = UnitItem.valueOf(record.get(5));

                            item.setCategory(CategoryItem.ALIMENTO);
                            item.setExpirationDate(expirationDate);
                            item.setUnit(unit);

                            donation.addItem(item);
                        }

                        if (category == "HIGIENE") {
                            HygieneTypeItem hygieneType = HygieneTypeItem.valueOf(record.get(4));

                            item.setCategory(CategoryItem.HIGIENE);
                            item.setHygieneType(hygieneType);

                            donation.addItem(item);
                        }

                        for (ItemEntity itemEntity : donation.getItems()) {
                            System.out.println(itemEntity);
                        }
                    }
                    catch (IllegalArgumentException exception) {
                        exception.printStackTrace();
                    }

                    // donationController.saveMany(donation);
                }
            }
            catch (OperationCancelledException exception) {
                System.out.println(exception.getMessage());
                Boolean finish = component.confirmation("Deseja voltar para o menu?");
                if (finish) break;
            }
            catch (IOException | CsvValidationException e) {
                System.out.println("Erro durante a leitura do arquivo csv selecionado!");
            }
        }
    }

    private List<List<String>> readCSVFile(String filePath) throws CsvValidationException, IOException {
        try {
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);
            CSVReader csvReader = new CSVReader(fileReader);

            List<List<String>> records = new ArrayList<List<String>>();
            String[] recoredTemp = null;

            while ((recoredTemp = csvReader.readNext()) != null) {
                List<String> record = new ArrayList<String>();
                for (String cell : recoredTemp) {
                    record.add(cell);
                }
                records.add(record);
            }

            return records;
        }
        catch (CsvValidationException | IOException exception) {
            throw exception;
        }
    }
}
