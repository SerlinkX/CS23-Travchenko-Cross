import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main3 extends Application {

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        Scene scene = new Scene(root, 800, 600);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Function and Its Derivative");

        XYChart.Series<Number, Number> functionSeries = new XYChart.Series<>();
        functionSeries.setName("Function");

        XYChart.Series<Number, Number> derivativeSeries = new XYChart.Series<>();
        derivativeSeries.setName("Derivative");

        // Test function 1: f(x) = exp(-x^2) * sin(x)
        double precision = 0.00001;
        for (double x = 1.5; x <= 6.5; x += 0.05) {
            double fx = Math.exp(-x * x) * Math.sin(x);
            double dfx = (Math.exp(-x * x) * Math.cos(x)) - (2 * x * fx);

            functionSeries.getData().add(new XYChart.Data<>(x, fx));
            derivativeSeries.getData().add(new XYChart.Data<>(x, dfx));
        }

        // Test function 2: f(x) = exp(-a*(x^2)) * sin(x) for a = 0.5, 1.0, 1.5
        double[] valuesOfA = {0.5, 1.0, 1.5};
        for (double a : valuesOfA) {
            for (double x = 1.5; x <= 6.5; x += 0.05) {
                double fx = Math.exp(-a * (x * x)) * Math.sin(x);
                double dfx = (Math.exp(-a * (x * x)) * Math.cos(x)) - (2 * a * x * fx);

                functionSeries.getData().add(new XYChart.Data<>(x, fx));
                derivativeSeries.getData().add(new XYChart.Data<>(x, dfx));
            }
        }

        // Test function 3: Reading function values from a text file
        ArrayList<Double> xValues = new ArrayList<>();
        ArrayList<Double> sinValues = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("function_values.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                xValues.add(Double.parseDouble(parts[0]));
                sinValues.add(Double.parseDouble(parts[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < xValues.size(); i++) {
            double x = xValues.get(i);
            double sinX = sinValues.get(i);
            double dfx = Math.cos(x);

            functionSeries.getData().add(new XYChart.Data<>(x, sinX));
            derivativeSeries.getData().add(new XYChart.Data<>(x, dfx));
        }

        chart.getData().addAll(functionSeries, derivativeSeries);
        root.getChildren().add(chart);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}