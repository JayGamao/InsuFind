package com.example.project;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;



public class Database {
    private File file = null;
    private FileWriter fWrite = null;
    private FileReader fRead = null;
    private Scanner scan = null;
    private Vector<String> row;
    private Vector<String> column;
    private Vector<String> userData;
    private Vector<Integer> insuranceIndexes;

    private String name;
    private String firstName;
    private String lastName;
    private String middleName;
    private String age;
    private String problems;
    private String date;
    private String insurance;
    private ObservableList<Database> list;
    private int selectedIndex;


    public void updateServices(int index, String newServices) {
        String fileName = "Service.txt";
        updateLineByIndex(fileName, index, newServices);
    }

    public void updateHospitalInfo(int index, String newInfo) {
        String fileName = "HospitalInfo.txt";
        updateLineByIndex(fileName, index, newInfo);
    }

    private void updateLineByIndex(String fileName, int index, String newLine) {
        try {
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();

            String line;
            int currentIndex = 0;
            while ((line = br.readLine()) != null) {
                if (currentIndex == index) {
                    line = newLine;
                }
                sb.append(line);
                sb.append(System.lineSeparator());
                currentIndex++;
            }
            br.close();

            FileWriter fw = new FileWriter(file);
            fw.write(sb.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Database() {
    }

    public Database(String filename) {
        this.file = new File(filename);
    }


    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public String getFilename() {
        return this.file.getName();
    }

    public void loadtocombobox(ComboBox<String> combo) {
        try {
            this.fRead = new FileReader(this.file);
            this.scan = new Scanner(this.fRead);
            while (this.scan.hasNext()) {
                combo.getItems().add(this.scan.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Database(String name, String age, String problems, String insurance, String date){
        this.name = name;
        this.age = age;
        this.problems = problems;
        this.insurance = insurance;
        this.date = date;
    }
    public void loadtolistview(List<String> list) {
        try {
            this.fRead = new FileReader(this.file);
            this.scan = new Scanner(this.fRead);
            while (this.scan.hasNext()) {
                list.add(this.scan.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadToString() {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            this.fRead = new FileReader(this.file);
            this.scan = new Scanner(this.fRead);

            while (this.scan.hasNext()) {
                stringBuilder.append(this.scan.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();

    }
    public void readDataFromFile(String fileName, ObservableList<Database> list) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length == 7) {
                    String name = data[0].trim() + " " + data[1].trim() + " " + data[2].trim();
                    String age = data[3].trim();
                    String problems = data[4].trim();
                    String insurance = data[5].trim();
                    String date = data[6].trim();
                    list.add(new Database(name, age, problems, insurance, date));
                    setList(list);

                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public String printLineByIndex(int index) {
        try {
            this.fRead = new FileReader(this.file);
            this.scan = new Scanner(this.fRead);

            int lineNumber = 0;
            while (this.scan.hasNext()) {
                String line = this.scan.nextLine();
                if (lineNumber == index) {
                    return line;
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // or an appropriate default value if line not found
    }
    public String readServices(int index) {
        String fileName = "Service.txt";
        String services = "";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int currentIndex = 0;
            while ((line = br.readLine()) != null) {
                if (currentIndex == index) {
                    services = line.trim();
                    break;
                }
                currentIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return services;
    }
    public String readHospitalInfo(int index) {
        String fileName = "HospitalInfo.txt";
        String hospitalInfo = "";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int currentIndex = 0;
            while ((line = br.readLine()) != null) {
                if (currentIndex == index) {
                    hospitalInfo = line.trim();
                    break;
                }
                currentIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hospitalInfo;
    }
    public void storeHospitalToFile(String selectedHospital, Vector<String> userData) {
        String filename = "";

        if (selectedHospital.equals("Mayo Clinic")) {
            filename = "MayoClinic.txt";
        } else if (selectedHospital.equals("Cleveland Clinic")) {
            filename = "ClevelandClinic.txt";
        } else if (selectedHospital.equals("Massachusetts General")) {
            filename = "MassGeneral.txt";
        } else if (selectedHospital.equals("Johns Hopkins")) {
            filename = "JohnsHopkins.txt";
        } else if (selectedHospital.equals("Charité")) {
            filename = "Universitätsmedizin Berlin - Berlin, Germany.txt";
        } else if (selectedHospital.equals("Toronto General")) {
            filename = "TorontoGeneral.txt";
        } else if (selectedHospital.equals("Singapore General")) {
            filename = "SingaporeGeneral.txt";
        } else if (selectedHospital.equals("Karolinska University")) {
            filename = "KarolinskaUniversity.txt";
        } else if (selectedHospital.equals("King's College")) {
            filename = "KingsCollege.txt";
        } else if (selectedHospital.equals("Bumrungrad International")) {
            filename = "BumrungradInternational.txt";
        } else if (selectedHospital.equals("Davao Doc")) {
            filename = "DavaoDoc.txt";
        } else if (selectedHospital.equals("SPC")) {
            filename = "SPC.txt";
        } else if (selectedHospital.equals("Adventist")) {
            filename = "Adventist.txt";
        }
        if (!filename.isEmpty()) {
            try {
                Database db = new Database(filename);
                db.storeToFile(userData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void storeToFile(Vector<String> records) {
        try {
            fWrite = new FileWriter(file, true); // Append mode
            boolean fileExists = file.exists();
            if (!fileExists) {}
            for (int i = 0; i < records.size(); i++) {
                String record = records.get(i);
                fWrite.write(record);
                if (i < records.size() - 1) {
                    fWrite.write(",");
                }
            }
            fWrite.write("\n");
            fWrite.flush();
            fWrite.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public Vector<Integer> getIndexesOfMatchingLines(String filename, String searchString) {
        Vector<Integer> indexes = new Vector<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0; // Start with line 1

            while ((line = reader.readLine()) != null) {
                if (line.contains(searchString)) {
                    indexes.add(lineNumber);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return indexes;
    }

    public int getIndexByValue(String filename, String value) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                if (line.equals(value)) {
                    return index;
                }
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;  // Value not found
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for age
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    // Getter and Setter for problems
    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    // Getter and Setter for date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Getter and Setter for insurance
    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }


    public ObservableList<Database> getList() {
        return list;
    }

    public void setList(ObservableList<Database> list) {
        this.list = list;
    }}
