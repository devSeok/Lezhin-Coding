package lezhin.coding.domain.member.domain.repository;

import lezhin.coding.domain.member.dto.UserWithAdultContentResDto;

import java.util.List;

public interface MemberRepositoryCustom {

    List<UserWithAdultContentResDto> findUsersWithAdultContentViews();
}
