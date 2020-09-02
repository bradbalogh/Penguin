import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class LoadData {

    public static void main(String[] args) throws IOException {
        String[] files = {"./workbooks/Test Scores.xlsx", "./workbooks/Test Retake Scores.xlsx", "./workbooks/Student Info.xlsx"};
        HashMap<Integer, Integer> TestScores = null;
        HashMap<Integer, Integer> RetakeScores = null;
        ArrayList<ArrayList<String>> StudentInfo = null;

        // calls one of two methods to collect the data from the xlsx files
        System.out.println("Extracting data from workbook files...");
        for (int i = 0; i < files.length; i++) {
            if (i == 0) {
                TestScores = getScores(files[i], i);
            } else if (i == 1) {
                RetakeScores = getScores(files[i], i);
            }
            else if (i == 2) {
                StudentInfo = getData(files[i], i);
            }
            else{
                System.out.println("File Not Found");
            }
        }

        // sending our collected data to be evaluated
        EvaluateData evalData = new EvaluateData();
        Integer average = evalData.getAverages(TestScores,RetakeScores);
        ArrayList<String> IDs = evalData.getFemaleCsMajors(StudentInfo);
        // sending our data to be posted to the server
        PostData post = new PostData();
        post.postJson(average,IDs);
    }

    // method to put students id and scores into HashMap
    public static HashMap<Integer, Integer> getScores(String file, int i) throws IOException {
        File excelFile = new File(file);
        FileInputStream fis = new FileInputStream(excelFile);
        // create an xssf Workbook object for our xlsx Excel File
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        // gets first sheet in the file
        XSSFSheet sheet = workbook.getSheetAt(0);
        // row iterator
        Iterator<Row> rowIt = sheet.iterator();
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        // skips title row
        rowIt.next();
        while (rowIt.hasNext()) {
            Row row = rowIt.next();
            // iterate on cells for the current row
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell1 = cellIterator.next();
                Cell cell2 = cellIterator.next();
                // adds data to HashMap
                map.put(Integer.valueOf((int)cell1.getNumericCellValue()), Integer.valueOf((int)cell2.getNumericCellValue()));
            }
        }
        workbook.close();
        fis.close();
        return(map);
    }

    public static ArrayList<ArrayList<String>> getData(String file, int i) throws IOException {
        File excelFile = new File(file);
        FileInputStream fis = new FileInputStream(excelFile);
        // create an XSSF Workbook object for our XLSX Excel File
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        // gets first sheet in the file
        XSSFSheet sheet = workbook.getSheetAt(0);
        // row iterator
        Iterator<Row> rowIt = sheet.iterator();
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        // skips title row
        rowIt.next();
        while (rowIt.hasNext()) {
            Row row = rowIt.next();
            // iterate on cells for the current row
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                    Cell cell1 = cellIterator.next();
                    Cell cell2 = cellIterator.next();
                    Cell cell3 = cellIterator.next();
                    // adds data to 2D ArrayList
                    data.add(new ArrayList<String>(Arrays.asList((String.valueOf((int)cell1.getNumericCellValue())),cell2.toString(),cell3.toString())));
            }
        }
        workbook.close();
        fis.close();
        return(data);
    }

}

