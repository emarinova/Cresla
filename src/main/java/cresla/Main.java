package cresla;

import cresla.interfaces.InputReader;
import cresla.interfaces.Manager;
import cresla.interfaces.OutputWriter;
import cresla.io.ConsoleReader;
import cresla.io.ConsoleWriter;
import cresla.managers.ManagerImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        InputReader reader = new ConsoleReader();

        OutputWriter writer = new ConsoleWriter();

        Manager manager = new ManagerImpl(1, new HashMap<>(), new HashMap<>(), writer);

        String line = reader.readLine();

        while (true) {
            String[] tokens = line.split("\\s+");
            List<String> arguments = Arrays.stream(tokens).skip(1).collect(Collectors.toList());

            switch (tokens[0]) {
                case "Reactor":
                    writer.writeLine(manager.reactorCommand(arguments));
                    break;
                case "Module":
                    writer.writeLine(manager.moduleCommand(arguments));
                    break;
                case "Report":
                    writer.write(manager.reportCommand(arguments));
                    break;
                case "Exit":
                    writer.write(manager.exitCommand(arguments));
                    break;
                default:
                    writer.writeLine("Invalid command");
                    break;
            }
            if("Exit".equals(tokens[0])) {
                break;
            }
            line = reader.readLine();
        }
    }
}
