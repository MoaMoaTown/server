package com.themore.moamoatown.town.service;

import com.themore.moamoatown.town.dto.*;

import java.util.List;

/**
 * 타운 서비스 인터페이스
 * @author 임원정
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  	임원정        최초 생성
 * 2024.08.23   임원정        createTown 메소드 추가
 * 2024.08.26   임원정        getTotalTax 메소드 추가
 * 2024.08.26   임원정        getJobRequests, createJob, allowJobRequest 추가
 * 2024.08.26   임원정        위시 상품 생성, 삭제 메소드 추가
 * 2024.08.26   임원정        멤버 위시 상품 완료 처리 메소드 추가
 * 2024.08.26   임원정        멤버 위시 요청 리스트 조회 메소드 추가
 * 2024.08.27   임원정        퀘스트 생성, 퀘스트 현황 리스트 추가
 * 2024.08.28   임원정        퀘스트 요청 조회, 퀘스트 수행인 선정, 퀘스트 요청 완료 처리 추가
 * 2024.08.30   임원정        급여 지급 프로세스 추가
 * </pre>
 */

public interface TownService {
    // 타운 생성
    TownCreateInternalDTO createTown(TownCreateRequestDTO townCreateRequestDTO, Long memberId);
    // 타운 세금 현황 조회
    TownTaxResponseDTO getTotalTax(Long townId);
    // 역할 요청 현황 조회
    List<JobRequestsResponseDTO> getJobRequests(Long townId);
    // 역할 만들기
    void createJob(JobCreateRequestDTO requestDTO, Long townId);
    // 역할 선정
    void allowJobRequest(Long jobRequestId);
    // 퀘스트 생성
    void createQuest(QuestCreateRequestDTO requestDTO, Long townId);
    // 퀘스트 현황 리스트 조회
    List<QuestStatusListResponseDTO> getQuestStatusList(Long townId);
    // 퀘스트 요청(memberQuest) 조회
    List<MemberQuestRequestsResponseDTO> getMemberQuests(Long questId);
    // 퀘스트 수행인 선정
    void updateMemberQuestSelected(Long memberQuestId);
    // 퀘스트 요청 완료 처리
    void completeMemberQuest(Long memberQuestId);
    // 위시 상품 생성
    void createWishItem(WishItemCreateRequestDTO requestDTO, Long townId);
    // 위시 상품 삭제
    void deleteWishItem(Long wishId);
    // 위시 상품 완료 처리
    void completeMemberWishItem(Long memberWishId);
    // 위시 상품 요청 리스트 조회
    List<MemberWishRequestsResponseDTO> getMemberWishRequests(Long townId);
    // 급여 지급 프로세스
    void processPayroll();
}
