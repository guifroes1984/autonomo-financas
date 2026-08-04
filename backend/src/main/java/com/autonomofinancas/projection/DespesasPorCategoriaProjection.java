package com.autonomofinancas.projection;

import java.math.BigDecimal;

public interface DespesasPorCategoriaProjection {

    String getCategoria();

    BigDecimal getTotalDespesas();
    
}
