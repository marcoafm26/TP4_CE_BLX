package model;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
public class GenerateFile {
    public static void createFile(String path, List<Individuo> individuals, boolean isFunctionValues) throws IOException {
        File file = new File(path);
        if(!file.exists()){
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (Individuo individual : individuals) {
            if (isFunctionValues) {
                String row = Arrays.toString(individual.getAvaliation()).replace('[','(').replace(']',')');
                bufferedWriter.write(row);
                bufferedWriter.newLine();
            } else {
                String row = Arrays.toString(individual.getGenes()).replace('[','(').replace(']',')');
                bufferedWriter.write(row);
                bufferedWriter.newLine();
            }
        }
        bufferedWriter.close();
        fileWriter.close();
    }
}
