package com.godlevel_api.service;

import com.godlevel_api.repository.DeliveryTimeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeliveryTimeService {

    private final DeliveryTimeRepository repository;

    public DeliveryTimeService(DeliveryTimeRepository repository) {
        this.repository = repository;
    }

    public Map<String, Object> getDeliveryTimeComparison(
            LocalDateTime start,
            LocalDateTime end,
            Integer storeId,
            String channelName
    ) {
        // Busca os dados do período atual
        List<Map<String, Object>> currentWeek = repository.getDeliveryTimes(start, end, storeId, channelName);

        // Busca os dados da semana anterior
        List<Map<String, Object>> previousWeek = repository.getDeliveryTimes(start.minusDays(7), end.minusDays(7), storeId, channelName);

        // Calcula médias por dia da semana
        Map<Integer, Double> avgByDay = currentWeek.stream()
                .collect(Collectors.groupingBy(
                        row -> ((Number) row.get("weekday")).intValue(),
                        Collectors.averagingDouble(row -> ((Number) row.get("avg_delivery_seconds")).doubleValue())
                ));

        // Calcula médias por hora
        Map<Integer, Double> avgByHour = currentWeek.stream()
                .collect(Collectors.groupingBy(
                        row -> ((Number) row.get("hour")).intValue(),
                        Collectors.averagingDouble(row -> ((Number) row.get("avg_delivery_seconds")).doubleValue())
                ));

        // Descobre melhor e pior dia
        Map.Entry<Integer, Double> bestDay = avgByDay.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .orElse(null);

        Map.Entry<Integer, Double> worstDay = avgByDay.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        // Descobre melhor e pior hora
        Map.Entry<Integer, Double> bestHour = avgByHour.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .orElse(null);

        Map.Entry<Integer, Double> worstHour = avgByHour.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        // Converte número do dia (0=Domingo, 1=Segunda...) para nome
        Map<Integer, String> dayNames = Map.of(
                0, "Domingo",
                1, "Segunda-feira",
                2, "Terça-feira",
                3, "Quarta-feira",
                4, "Quinta-feira",
                5, "Sexta-feira",
                6, "Sábado"
        );

        // Calcula médias gerais (para comparar se piorou)
        double currentAvg = currentWeek.stream()
                .mapToDouble(r -> ((Number) r.get("avg_delivery_seconds")).doubleValue())
                .average().orElse(0);

        double previousAvg = previousWeek.stream()
                .mapToDouble(r -> ((Number) r.get("avg_delivery_seconds")).doubleValue())
                .average().orElse(0);

        double variation = currentAvg - previousAvg;
        boolean gotWorse = variation > 0;

        // Monta resposta
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("best_day", bestDay != null ? dayNames.get(bestDay.getKey()) : null);
        response.put("worst_day", worstDay != null ? dayNames.get(worstDay.getKey()) : null);
        response.put("best_hour", bestHour != null ? bestHour.getKey() : null);
        response.put("worst_hour", worstHour != null ? worstHour.getKey() : null);
        response.put("average_delivery_seconds_by_day", avgByDay);
        response.put("average_delivery_seconds_by_hour", avgByHour);
        response.put("current_average", currentAvg);
        response.put("previous_average", previousAvg);
        response.put("difference_in_seconds", variation);
        response.put("delivery_got_worse", gotWorse);

        return response;
    }
}
