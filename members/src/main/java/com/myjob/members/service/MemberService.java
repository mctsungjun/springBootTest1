package com.myjob.members.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.myjob.members.dto.MemberDto;
import com.myjob.members.entity.MemberEntity;
import com.myjob.members.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    
    private final MemberRepository memberRepository;

    public void save(MemberDto dto){
        //1.dto -> entity 변환
        MemberEntity memberEntity = MemberEntity.toMemberEntity(dto);
        //2.repository의 save 메서드 호출
        memberRepository.save(memberEntity);
        //repositoryd의 save메서드 호출 (조건.entity객체를 넘겨줘야함)


    }

    public MemberDto login(MemberDto dto){
        /*
         * 1.회원이 입력한 이메일로 DB에서 조회를 함
         * 2.DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판다
         */

       Optional<MemberEntity> byMemberEntity = memberRepository.findByMemberEmail(dto.getMemberEmail());
       if(byMemberEntity.isPresent()){
        // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다.)
        MemberEntity memberEntity = byMemberEntity.get();
        if(memberEntity.getMeberPassword().equals(dto.getMemberPassword())){
            // 비밀번호가 일치
            //entity 객체를 dto  변환
            MemberDto memberDto = MemberDto.toMemberDto(memberEntity);
            return memberDto;
        }else{
            //비밀번호 불일치
            return null;
        }

       }else{
        // 조회 결과가 없다(해당 이메일을 가진 회원 정보가 없다.)
        return null;
       }
    }

    public List<MemberDto> findAll(){
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();
        for(MemberEntity memberEntity: memberEntityList){
            MemberDto memberDto = MemberDto.toMemberDto(memberEntity);
            memberDtoList.add(memberDto);
            
        }
        return memberDtoList;
    }

    public MemberDto findById(Long id){
        Optional<MemberEntity> byId = memberRepository.findById(id);
        if(byId.isPresent()){
            return MemberDto.toMemberDto(byId.get());
        }else{
            return null;
        }
    }

    public MemberDto updateForm(String myEmail){
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(myEmail);
        if(byMemberEmail.isPresent()){
            return MemberDto.toMemberDto(byMemberEmail.get());
        }else{
            return null;
        }
        
    }
    public void update(MemberDto memberDto){
        
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDto));
    }
    public void deleteMember(Long id){
        memberRepository.deleteById(id);
    }
}
