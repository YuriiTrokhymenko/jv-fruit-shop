package сore.basesyntax.service;

import сore.basesyntax.model.FruitTransaction;

import java.util.List;

public interface ReaderService {
    List<FruitTransaction> readFromFile(String filePath);
}
