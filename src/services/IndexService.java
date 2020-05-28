package services;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class IndexService {

    private static IndexService instance;

    public static IndexService getInstance() {
        if (instance == null)
            instance = new IndexService();
        return instance;
    }

    // recebe o conteudo de index e retorna um mapping que faz o de-para entre nome cod e nome real dos arquivos listados
//    public Map<String, String> getIndexInfo(byte[] index) throws UnsupportedEncodingException {
//        String indexContent = new String(index, "UTF8");
//        String[] indexContentLines = indexContent.split("\n");
//        Map<String, String> indexInfo = new HashMap<>();
//
//        for (String line : indexContentLines) {
//            String[] splitedLine = line.split(" ");
//            indexInfo.put(splitedLine[0], splitedLine[1]);
//
//            System.out.println(splitedLine[0] + " " + splitedLine[1]);
//        }
//
//        return indexInfo;
//    }

    public String[][] getIndexInfo(byte[] index) throws UnsupportedEncodingException {
        String indexContent = new String(index, "UTF8");
        String []indexContentLines = indexContent.split("\n");

        String[][] indexInfo = new String[indexContentLines.length][4];
        for(int i = 0; i < indexContentLines.length ; i ++){
            indexInfo[i][0] = indexContentLines[i].split(" ")[0];
            indexInfo[i][1] = indexContentLines[i].split(" ")[1];
            indexInfo[i][2] = indexContentLines[i].split(" ")[2];
            indexInfo[i][3] = indexContentLines[i].split(" ")[3];
        }
        return indexInfo;

    }
}
