package Util;

import Util.model.RegistroModel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelRead {
    String archivo;
    List<RegistroModel> datos;
    public ExcelRead(){
        this.archivo = "C:\\DatosRegistro.xlsx";
        this.datos = new ArrayList<RegistroModel>();
    }
    public ExcelRead(String archivo){
        this.archivo = archivo;
        this.datos = new ArrayList<RegistroModel>();
    }

    public List<RegistroModel> LecturaArchivo() throws InvalidFormatException, IOException {
        //Leer el archivo el inputStream funciona con versiones anteriores de excel
        //FileInputStream fileStream = new FileInputStream(new File("C:\\SeleniumCourse\\archivo.xls"));
        OPCPackage pkg = OPCPackage.open(new File(archivo));
        //crear la instancia para el libro de excel para versiones de excel viejas
        //HSSFWorkbook workBook = new HSSFWorkbook(fileStream);
        //para versiones nuevas
        XSSFWorkbook workBook = new XSSFWorkbook(pkg);
        //crear la instancia para el sheet de excel para versiones anteriores
        //HSSFSheet sheet = workBook.getSheetAt(0);
        XSSFSheet sheet = workBook.getSheetAt(0);
        //Inicia la lectura
        FormulaEvaluator formulaEvaluator = workBook.getCreationHelper().createFormulaEvaluator();
        int i=0;
        for(Row row : sheet){
            if(i > 0) {
                RegistroModel datoCliente = new RegistroModel();
                int j = 0;
                for (Cell cell : row) {
                    if(j<6) {
                        switch (formulaEvaluator.evaluateInCell(cell).getCellType()) {
                            case STRING: {
                                switch (j) {
                                    case 0: {
                                        datoCliente.setName(cell.getRichStringCellValue().getString());
                                        j++;
                                        break;
                                    }
                                    case 1: {
                                        datoCliente.setCountry(cell.getRichStringCellValue().getString());
                                        j++;
                                        break;
                                    }
                                    case 2: {
                                        datoCliente.setCity(cell.getRichStringCellValue().getString());
                                        j++;
                                        break;
                                    }
                                    case 3: {
                                        datoCliente.setCreditCard( cell.getRichStringCellValue().getString());
                                        j++;
                                        break;
                                    }
                                }
                                break;
                            }
                            case NUMERIC:{
                                switch(j) {
                                    case 4: {
                                        datoCliente.setMonth(String.valueOf( Integer.valueOf ((int) cell.getNumericCellValue())));
                                        j++;
                                        break;
                                    }
                                    case 5: {
                                        datoCliente.setYear(String.valueOf( Integer.valueOf ((int) cell.getNumericCellValue())));
                                        j++;
                                        break;
                                    }
                                }
                            }
                            default:{
                                //System.out.println(cell.getCellType());
                                break;
                            }
                        }
                    }
                }
                this.datos.add(datoCliente);
            }
            i++;
        }
        return this.datos;
    }
}
