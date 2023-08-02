package lezhin.coding.domain.member.service;

import lezhin.coding.domain.member.dto.UserWithAdultContentResDto;

import java.util.List;

public interface MemberService {


    List<UserWithAdultContentResDto> findUsersWithAdultContentViews();

    void memberDelete(Long memberId);
}
