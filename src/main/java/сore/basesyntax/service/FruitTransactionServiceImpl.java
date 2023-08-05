package сore.basesyntax.service;

import сore.basesyntax.db.Storage;
import сore.basesyntax.model.FruitTransaction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FruitTransactionServiceImpl implements FruitTransactionService {
    private OperationStrategy operationStrategy;
    private static final FruitTransaction.Operation OPERATION_BALANCE = FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation OPERATION_TOTAL = FruitTransaction.Operation.TOTAL;

    public FruitTransactionServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void CalcFruitTransaction() {
        Map<String, List<FruitTransaction>> transactionMap = Storage.fruitTransactions.stream()
                .collect(Collectors.groupingBy(FruitTransaction::getFruit,
                        Collectors.mapping(fruitTransaction -> fruitTransaction,
                                Collectors.toList())));
        for (Map.Entry<String, List<FruitTransaction>> entry : transactionMap.entrySet()) {
            String fruit = entry.getKey();
            int summaResult = 0;
            List<FruitTransaction> fruitTransactions = entry.getValue();
            for (FruitTransaction fruitTransaction : fruitTransactions) {
                summaResult = operationStrategy.get(fruitTransaction.getOperation())
                        .getOperation(summaResult,fruitTransaction.getQuantity());
            }
            if (summaResult < 0) {
                throw new RuntimeException("Balance must not be negative");
            }
            FruitTransaction newFruitTransaction = new FruitTransaction();
            newFruitTransaction.setOperation(OPERATION_TOTAL);
            newFruitTransaction.setFruit(fruit);
            newFruitTransaction.setQuantity(summaResult);
            Storage.fruitTransactions.add(newFruitTransaction);
        }
    }
}
