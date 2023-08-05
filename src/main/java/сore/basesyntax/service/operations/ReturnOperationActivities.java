package сore.basesyntax.service.operations;

public class ReturnOperationActivities implements OperationActivities {
    @Override
    public int getOperation(int balance, int amount) {
        return balance + amount;
    }
}
