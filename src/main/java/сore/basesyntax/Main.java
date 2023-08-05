package сore.basesyntax;

import сore.basesyntax.model.FruitTransaction;
import сore.basesyntax.service.*;
import сore.basesyntax.service.operations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String FILE_PATH = "src/main/resources/";
    private static final String FILE_FROM = "fruits.csv";
    private static final String FILE_TO = "fruitsResult.csv";

    public static void main(String[] args) {
        ReaderService readerService = new ReaderServiceImpl();
        readerService.readFromFile(FILE_PATH + FILE_FROM);

        Map<FruitTransaction.Operation,OperationActivities> operationStrategiesMap = new HashMap<>();
        operationStrategiesMap.put(FruitTransaction.Operation.BALANCE,new BalanceOperationActivities());
        operationStrategiesMap.put(FruitTransaction.Operation.SUPPLY,new SupplyOperationActivities());
        operationStrategiesMap.put(FruitTransaction.Operation.PURCHASE,new PurchaseOperationActivities());
        operationStrategiesMap.put(FruitTransaction.Operation.RETURN,new ReturnOperationActivities());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationStrategiesMap);
        FruitTransactionService fruitTransactionService = new FruitTransactionServiceImpl(operationStrategy);
        fruitTransactionService.CalcFruitTransaction();

        TotalService totalService = new TotalServiceImpl();
        String strReport = totalService.getReport();
        WriterService writerService = new WriterServiceImpl();
        writerService.writeToFile(strReport,FILE_PATH + FILE_TO);
    }
}
