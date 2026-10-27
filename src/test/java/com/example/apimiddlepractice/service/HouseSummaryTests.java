package com.example.apimiddlepractice.service;

import com.example.apimiddlepractice.dto.CategorySummary;
import com.example.apimiddlepractice.exception.HouseArgumentNotValidException;
import com.example.apimiddlepractice.repository.HouseRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HouseSummaryTests {
    private final HouseRepository repository = mock(HouseRepository.class);
    private final HouseService service = new HouseService(repository);

    @Test
    void totalsIncomeAndExpenseUsingLongAmounts() {
        when(repository.selectCategorySummary(LocalDate.of(2026, 10, 1), LocalDate.of(2026, 11, 1)))
                .thenReturn(List.of(category("収入", "給与", 3_000_000_000L),
                        category("支出", "食費", 30_000), category("支出", "交通費", 10_000)));

        var summary = service.selectMonthlySummary("2026-10");

        assertEquals("2026-10", summary.getMonth());
        assertEquals(3_000_000_000L, summary.getIncome());
        assertEquals(40_000, summary.getExpense());
        assertEquals(2_999_960_000L, summary.getBalance());
    }

    @Test
    void emptyMonthReturnsZeroTotals() {
        when(repository.selectCategorySummary(any(), any())).thenReturn(List.of());
        var summary = service.selectMonthlySummary("2026-10");
        assertEquals(0, summary.getIncome());
        assertEquals(0, summary.getExpense());
        assertEquals(0, summary.getBalance());
        assertTrue(service.selectCategorySummary("2026-10").isEmpty());
    }

    @Test
    void monthRangeHandlesLeapYearAndYearBoundary() {
        when(repository.selectCategorySummary(any(), any())).thenReturn(List.of());
        service.selectCategorySummary("2024-02");
        verify(repository).selectCategorySummary(LocalDate.of(2024, 2, 1), LocalDate.of(2024, 3, 1));
        service.selectCategorySummary("2026-12");
        verify(repository).selectCategorySummary(LocalDate.of(2026, 12, 1), LocalDate.of(2027, 1, 1));
    }

    @Test
    void invalidMonthIsRejectedBeforeQuerying() {
        for (String month : new String[]{null, "", "2026-1", "2026-13", "0000-01", "2026-02-01"}) {
            assertThrows(HouseArgumentNotValidException.class, () -> service.selectCategorySummary(month));
        }
        verifyNoInteractions(repository);
    }

    private CategorySummary category(String type, String category, long amount) {
        CategorySummary summary = new CategorySummary();
        summary.setType(type);
        summary.setCategory(category);
        summary.setTotalAmount(amount);
        return summary;
    }
}
