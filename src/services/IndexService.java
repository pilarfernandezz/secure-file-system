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
    public Map<String, String> getIndexInfo(byte[] index) throws UnsupportedEncodingException {
        String indexContent = new String(index, "UTF8");
        String[] indexContentLines = indexContent.split("\n");
        Map<String, String> indexInfo = new HashMap<>();

        for (String line : indexContentLines) {
            String[] splitedLine = line.split(" ");
            indexInfo.put(splitedLine[0], splitedLine[1]);
        }

        return indexInfo;
    }
}
