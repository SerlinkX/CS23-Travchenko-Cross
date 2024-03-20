import java.util.ArrayList;

// Клас для представлення аналітичної функції та обчислення похідних
class AnalyticalFunction {
    // Представлення функції
    static double function(double x) {
        return Math.exp(-x*x) * Math.sin(x);
    }

    // Обчислення похідної
    static double derivative(double x) {
        return Math.exp(-x*x) * (2*x*Math.sin(x) + Math.cos(x));
    }
}

// Клас для представлення табличної функції та обчислення похідних
class TabularFunction {
    // Таблиця значень функції
    private ArrayList<Double> xValues;
    private ArrayList<Double> yValues;

    // Конструктор для ініціалізації таблиці значень
    public TabularFunction(ArrayList<Double> xValues, ArrayList<Double> yValues) {
        this.xValues = xValues;
        this.yValues = yValues;
    }

    // Представлення функції
    double function(double x) {
        // Знайти ближче значення в таблиці та інтерполювати
        int index = findClosestIndex(x);
        double x1 = xValues.get(index);
        double y1 = yValues.get(index);
        double x2 = xValues.get(index + 1);
        double y2 = yValues.get(index + 1);

        // Інтерполяція лінійна
        return y1 + (y2 - y1) / (x2 - x1) * (x - x1);
    }

    // Обчислення похідної
    double derivative(double x) {
        // Знайти ближче значення в таблиці та обчислити похідну чисельно
        double h = 1e-5; // Малий крок для обчислення похідної
        return (function(x + h) - function(x - h)) / (2 * h);
    }

    // Допоміжний метод для знаходження ближчого значення в таблиці
    private int findClosestIndex(double x) {
        int index = 0;
        double minDiff = Math.abs(x - xValues.get(0));

        for (int i = 1; i < xValues.size(); i++) {
            double diff = Math.abs(x - xValues.get(i));
            if (diff < minDiff) {
                minDiff = diff;
                index = i;
            }
        }

        return index;
    }
}

// Головний клас для тестування
public class Main {
    public static void main(String[] args) {
        // Тестування аналітичної функції
        testAnalyticalFunction();

        // Тестування табличної функції
        testTabularFunction();
    }

    private static void testAnalyticalFunction() {
        System.out.println("Analytical Function Test:");
        for (double x = 1.5; x <= 6.5; x += 0.05) {
            double y = AnalyticalFunction.function(x);
            double dy = AnalyticalFunction.derivative(x);
            System.out.printf("x=%.2f, f(x)=%.4f, f'(x)=%.4f\n", x, y, dy);
        }
    }

    private static void testTabularFunction() {
        // Приклад табличної функції
        ArrayList<Double> xValues = new ArrayList<>();
        ArrayList<Double> yValues = new ArrayList<>();

        // Додавання значень до таблиці
        for (double x = 1.5; x <= 6.5; x += 0.1) {
            xValues.add(x);
            yValues.add(Math.sin(x));
        }

        TabularFunction tabularFunction = new TabularFunction(xValues, yValues);

        System.out.println("\nTabular Function Test:");
        for (double x = 1.5; x <= 6.5; x += 0.05) {
            double y = tabularFunction.function(x);
            double dy = tabularFunction.derivative(x);
            System.out.printf("x=%.2f, f(x)=%.4f, f'(x)=%.4f\n", x, y, dy);
        }
    }
}