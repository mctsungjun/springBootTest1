package com.myjob.members.entity;

import com.myjob.members.dto.MemberDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="member_table")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String memberEmail;

    @Column
    private String memberName;

    @Column
    private String meberPassword;
    
    
    public static MemberEntity toMemberEntity(MemberDto memberDto){
        MemberEntity memberEntity = new MemberEntity();
        
        memberEntity.setMeberPassword(memberDto.getMemberPassword());
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberName(memberDto.getMemberName());
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDto memberDto){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDto.getId());
        memberEntity.setMeberPassword(memberDto.getMemberPassword());
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberName(memberDto.getMemberName());
        return memberEntity;
    }


}
