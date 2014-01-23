
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class ReadXL {

    /**
     * ruta del documento a leer
     */
    public static String documentoDir = "/home/nico/Trabajo/Lubricentro/Excel/PROVEEDOR.xls";

    public static void main(String argv[]) throws IOException {
//  try{
        // Se crea una referencia al documento excel
        
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(documentoDir));
        // indicamos la hoja que queremos leer
        HSSFSheet sheet = workbook.getSheet("Hoja1");
        Cell celdaNombre;
        Cell telefono;
        String telString;
        Row rss = sheet.getRow(32);
        Iterator<Row> iterarRow = sheet.iterator();
        while (iterarRow.hasNext()) {
            Row row = iterarRow.next();
            celdaNombre = row.getCell(0);
            telefono = row.getCell(1);
            telString="";
            if (celdaNombre != null) {
                if(!celdaNombre.toString().isEmpty()){
                celdaNombre.setCellType(Cell.CELL_TYPE_STRING);
                String nombre=celdaNombre.getStringCellValue();
                if (telefono != null) {
                    telefono.setCellType(Cell.CELL_TYPE_STRING);
                    telString = telefono.getStringCellValue();
                }
                    System.out.println(celdaNombre.getRowIndex() + " " + nombre + "    Telefono: " + telString);
            }
            }

        }

        //recorremos filas
   /*for (Row r : sheet){
         for (Cell c: r)
         {
         if(c!=null){
         try{
         if(c.getCellType()==Cell.CELL_TYPE_STRING){
         System.out.println("STRING CELL--> " + c.getStringCellValue());
         }
         else if (c.getCellType()==Cell.CELL_TYPE_NUMERIC){
         System.out.println("NUMERIC CELL--> " + c.getNumericCellValue());
         }
         }catch(Exception e){e.printStackTrace();e=null;}
         }
         }
         }*/
        // }catch(Exception e) {
        //  System.out.println("Error! " + e );
        // }
    }
}