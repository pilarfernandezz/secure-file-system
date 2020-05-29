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

    private IndexService(){}

    public String[][] getIndexInfo(byte[] index) throws Exception {
        try {
            String indexContent = new String(index, "UTF8");
            String[] indexContentLines = indexContent.split("\n");

            String[][] indexInfo = new String[indexContentLines.length][4];
            for (int i = 0; i < indexContentLines.length; i++) {
                indexInfo[i][0] = indexContentLines[i].split(" ")[0];
                indexInfo[i][1] = indexContentLines[i].split(" ")[1];
                indexInfo[i][2] = indexContentLines[i].split(" ")[2];
                indexInfo[i][3] = indexContentLines[i].split(" ")[3];
            }
            return indexInfo;
        }catch (UnsupportedEncodingException e){
            throw new Exception("Ocorreu um erro ao obter informações da pasta segura: " + e.getMessage());
        }
    }
}
