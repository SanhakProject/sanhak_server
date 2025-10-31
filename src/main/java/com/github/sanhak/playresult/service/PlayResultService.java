package com.github.sanhak.playresult.service;

import com.github.sanhak.playresult.controller.dto.response.PlayResultFindAllResponse.PlayResultSummary;
import com.github.sanhak.playresult.controller.dto.response.PlayResultFindAllResponse;
import com.github.sanhak.playresult.repository.PlayResultEntity;
import com.github.sanhak.playresult.repository.PlayResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayResultService {

    private final PlayResultRepository playResultRepository;

    public PlayResultFindAllResponse getPlayResults(Long userId) {
        List<PlayResultEntity> results = playResultRepository
                .findByUserIdOrderByPlayedAtDesc(userId);

        List<PlayResultSummary> items = results.stream()
                .map(PlayResultSummary::from)
                .toList();

        return new PlayResultFindAllResponse(items);
    }
}