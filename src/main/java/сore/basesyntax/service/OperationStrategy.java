package сore.basesyntax.service;

import сore.basesyntax.model.FruitTransaction;
import сore.basesyntax.service.operations.OperationActivities;

public interface OperationStrategy {
    OperationActivities get(FruitTransaction.Operation operation);
}
