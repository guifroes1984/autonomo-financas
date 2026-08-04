package com.autonomofinancas.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface EvolucaoDiariaProjection {

    LocalDate getData();

    BigDecimal getTotalReceitas();

    BigDecimal getTotalDespesas();

    
}
