package com.themore.moamoatown.town.mapper;

import com.themore.moamoatown.town.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 타운 매퍼 인터페이스
 * @author 임원정
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  	임원정        최초 생성
 * 2024.08.23  	임원정        타운 만들기 기능 추가
 * 2024.08.26   임원정        타운 세금 현황 조회 추가
 * 2024.08.26   임원정        selectJobRequestByTownId, insertJob, updateJobRequestAllowed 추가
 * 2024.08.26   임원정        insertWish, deleteWish, deleteMemberWish, updateMemberWishCompleted 메소드 추가
 * 2024.08.27   임원정        selectWishRequestsByTownId 메소드 추가
 * 2024.08.27   임원정        insertQuest, selectQuestStatusListByTownId 메소드 추가
 * 2024.08.28   임원정        findMemberIdByJobRequestId 추가
 * 2024.08.28   임원정        selectMemberQuestByQuestId, updateMemberQuestSelected 메소드 추가
 * 2024.08.28   임원정        callCompleteQuestProcedure, findMemberIdByMemberQuestId 메소드 추가
 * 2024.08.30   임원정        callProcessPayrollProcedure 메소드 추가
 * 2024.09.04   임원정        타운 세금 현황 조회 삭제, 페이지네이션 적용
 * </pre>
 */

@Mapper
public interface TownMapper {
    /** 타운 만들기 **/
    Long selectIdByTownCode(String townCode);   // 타운 코드로 타운 ID 조회
    int insertTown(TownCreateRequestDTO townCreateRequestDTO);  // 타운 삽입
    int updateMember(@Param("townId") Long townId, @Param("memberId") Long memberId);   // 멤버의 타운아이디 및 역할 업데이트

    // 타운 역할 신청 현황 조회
    List<JobRequestsResponseDTO> selectJobRequestByTownId(@Param("townId") Long townId, @Param("cri") Criteria cri);
    // 역할 만들기
    int insertJob(JobCreateRequestDTO jobCreateRequestDTO);
    // 역할 요청 승인
    int updateJobRequestAllowed(Long jobRequestId);
    // 역할 승인된 회원 ID 조회
    Long findMemberIdByJobRequestId(Long jobRequestId);
    // 퀘스트 생성
    int insertQuest(QuestCreateRequestDTO questCreateRequestDTO);
    // 퀘스트 현황 리스트 조회
    List<QuestStatusListResponseDTO> selectQuestStatusListByTownId(@Param("townId")Long townId, @Param("cri") Criteria cri);
    // 퀘스트 신청 조회
    List<MemberQuestRequestsResponseDTO> selectMemberQuestByQuestId(@Param("questId")Long questId, @Param("cri") Criteria cri);
    // 퀘스트 수행인 선정
    int updateMemberQuestSelected(Long memberQuestId);
    // 퀘스트 완료 처리
    void callCompleteQuestProcedure(Long memberQuestId);
    // 퀘스트 요청 또는 완료 시 회원 ID 조회
    Long findMemberIdByMemberQuestId(Long memberQuestId);
    // 위시 상품 생성
    int insertWish(WishItemCreateRequestDTO createRequestDTO);
    /** 위시 삭제 **/
    int deleteMemberWish(Long wishId);  // 멤버 위시 삭제
    int deleteWish(Long wishId);    // 위시 삭제
    // 멤버 위시 상품 완료 처리
    int updateMemberWishCompleted(Long memberWishId);
    // 위시 상품 요청 현황
    List<MemberWishRequestsResponseDTO> selectWishRequestsByTownId(@Param("townId")Long townId, @Param("cri") Criteria cri);
    // 급여 지급 및 소득세 징수
    void callProcessPayrollProcedure();
}
