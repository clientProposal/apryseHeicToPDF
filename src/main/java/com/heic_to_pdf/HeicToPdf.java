package com.heic_to_pdf;

import java.io.File;
import java.nio.file.Paths;
import io.github.cdimascio.dotenv.Dotenv;
import com.pdftron.common.PDFNetException;
import com.pdftron.sdf.SDFDoc;
import com.pdftron.pdf.*;

public class HeicToPdf {
        public static void warning(Boolean isFound, String choice) {
            if (!isFound) {
                System.err.println(choice + " not found.");
            }
        }
        // Method warning, refactored some repetitive code. Used on line 38/39 below
        public static void heicToPDF() {
        String heic_input_file = "forest.heic";
        String input_path = System.getProperty("user.dir") + "/src/main/java/resources/";
        // System.getProperty("user.dir") = Portable, finds root of project
        String output_path = input_path + "output/";
        String output_file = output_path + heic_input_file.replace(".heic", "") + ".pdf";
        // heic_input_file.replace(".heic", "") -> get rid of old extension after conversion
        File input_file_to_test = Paths.get(input_path, "forest.heic").toFile();
        // Look for actual file, to make sure it exists
        Dotenv dotenv = Dotenv.configure()
            .directory("./")
            .ignoreIfMissing()
            .load();
        // Use dotenv to find my dotenv file, where my key for Apryse/PDFTron is stored
        String token = dotenv.get("PDFTRON_KEY");
        try {
            PDFNet.initialize(token);
            try {
                PDFNet.addResourceSearchPath(input_path + "lib/");
                // As shown in Apryse's samples
                warning(AdvancedImagingModule.isModuleAvailable(), "AdvancedImagingTest");
                warning(input_file_to_test.exists(), "Input file: " + input_file_to_test.getAbsolutePath());
            } catch (PDFNetException e) {
                e.printStackTrace();
            }
            try (PDFDoc doc = new PDFDoc()) {
                Convert.toPdf(doc, input_path + heic_input_file);
                doc.save(output_file, SDFDoc.SaveMode.LINEARIZED, null);
                // As shown in Apryse's samples
                System.out.println("Result saved in " + output_file);
            } catch (PDFNetException e) {
                System.out.println("Unable to convert HEIC document, error:");
                e.printStackTrace();
                System.out.println(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PDFNet.terminate();
        }
    }

}
