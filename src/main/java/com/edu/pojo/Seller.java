package com.edu.pojo;

import java.io.Serializable;
import java.util.Date;

public class Seller implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.seller_id
     *
     * @mbg.generated
     */
    private String sellerId;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.nick_name
     *
     * @mbg.generated
     */
    private String nickName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.password
     *
     * @mbg.generated
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.email
     *
     * @mbg.generated
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.mobile
     *
     * @mbg.generated
     */
    private String mobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.telephone
     *
     * @mbg.generated
     */
    private String telephone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.status
     *
     * @mbg.generated
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.address_detail
     *
     * @mbg.generated
     */
    private String addressDetail;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.linkman_name
     *
     * @mbg.generated
     */
    private String linkmanName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.linkman_qq
     *
     * @mbg.generated
     */
    private String linkmanQq;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.linkman_mobile
     *
     * @mbg.generated
     */
    private String linkmanMobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.linkman_email
     *
     * @mbg.generated
     */
    private String linkmanEmail;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.license_number
     *
     * @mbg.generated
     */
    private String licenseNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.tax_number
     *
     * @mbg.generated
     */
    private String taxNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.org_number
     *
     * @mbg.generated
     */
    private String orgNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.address
     *
     * @mbg.generated
     */
    private Long address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.logo_pic
     *
     * @mbg.generated
     */
    private String logoPic;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.brief
     *
     * @mbg.generated
     */
    private String brief;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.legal_person
     *
     * @mbg.generated
     */
    private String legalPerson;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.legal_person_card_id
     *
     * @mbg.generated
     */
    private String legalPersonCardId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.bank_user
     *
     * @mbg.generated
     */
    private String bankUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.bank_name
     *
     * @mbg.generated
     */
    private String bankName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.question
     *
     * @mbg.generated
     */
    private String question;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.answer
     *
     * @mbg.generated
     */
    private String answer;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column neuedu_seller.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table neuedu_seller
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.seller_id
     *
     * @return the value of neuedu_seller.seller_id
     *
     * @mbg.generated
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.seller_id
     *
     * @param sellerId the value for neuedu_seller.seller_id
     *
     * @mbg.generated
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.name
     *
     * @return the value of neuedu_seller.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.name
     *
     * @param name the value for neuedu_seller.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.nick_name
     *
     * @return the value of neuedu_seller.nick_name
     *
     * @mbg.generated
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.nick_name
     *
     * @param nickName the value for neuedu_seller.nick_name
     *
     * @mbg.generated
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.password
     *
     * @return the value of neuedu_seller.password
     *
     * @mbg.generated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.password
     *
     * @param password the value for neuedu_seller.password
     *
     * @mbg.generated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.email
     *
     * @return the value of neuedu_seller.email
     *
     * @mbg.generated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.email
     *
     * @param email the value for neuedu_seller.email
     *
     * @mbg.generated
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.mobile
     *
     * @return the value of neuedu_seller.mobile
     *
     * @mbg.generated
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.mobile
     *
     * @param mobile the value for neuedu_seller.mobile
     *
     * @mbg.generated
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.telephone
     *
     * @return the value of neuedu_seller.telephone
     *
     * @mbg.generated
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.telephone
     *
     * @param telephone the value for neuedu_seller.telephone
     *
     * @mbg.generated
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.status
     *
     * @return the value of neuedu_seller.status
     *
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.status
     *
     * @param status the value for neuedu_seller.status
     *
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.address_detail
     *
     * @return the value of neuedu_seller.address_detail
     *
     * @mbg.generated
     */
    public String getAddressDetail() {
        return addressDetail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.address_detail
     *
     * @param addressDetail the value for neuedu_seller.address_detail
     *
     * @mbg.generated
     */
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail == null ? null : addressDetail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.linkman_name
     *
     * @return the value of neuedu_seller.linkman_name
     *
     * @mbg.generated
     */
    public String getLinkmanName() {
        return linkmanName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.linkman_name
     *
     * @param linkmanName the value for neuedu_seller.linkman_name
     *
     * @mbg.generated
     */
    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName == null ? null : linkmanName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.linkman_qq
     *
     * @return the value of neuedu_seller.linkman_qq
     *
     * @mbg.generated
     */
    public String getLinkmanQq() {
        return linkmanQq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.linkman_qq
     *
     * @param linkmanQq the value for neuedu_seller.linkman_qq
     *
     * @mbg.generated
     */
    public void setLinkmanQq(String linkmanQq) {
        this.linkmanQq = linkmanQq == null ? null : linkmanQq.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.linkman_mobile
     *
     * @return the value of neuedu_seller.linkman_mobile
     *
     * @mbg.generated
     */
    public String getLinkmanMobile() {
        return linkmanMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.linkman_mobile
     *
     * @param linkmanMobile the value for neuedu_seller.linkman_mobile
     *
     * @mbg.generated
     */
    public void setLinkmanMobile(String linkmanMobile) {
        this.linkmanMobile = linkmanMobile == null ? null : linkmanMobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.linkman_email
     *
     * @return the value of neuedu_seller.linkman_email
     *
     * @mbg.generated
     */
    public String getLinkmanEmail() {
        return linkmanEmail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.linkman_email
     *
     * @param linkmanEmail the value for neuedu_seller.linkman_email
     *
     * @mbg.generated
     */
    public void setLinkmanEmail(String linkmanEmail) {
        this.linkmanEmail = linkmanEmail == null ? null : linkmanEmail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.license_number
     *
     * @return the value of neuedu_seller.license_number
     *
     * @mbg.generated
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.license_number
     *
     * @param licenseNumber the value for neuedu_seller.license_number
     *
     * @mbg.generated
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber == null ? null : licenseNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.tax_number
     *
     * @return the value of neuedu_seller.tax_number
     *
     * @mbg.generated
     */
    public String getTaxNumber() {
        return taxNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.tax_number
     *
     * @param taxNumber the value for neuedu_seller.tax_number
     *
     * @mbg.generated
     */
    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber == null ? null : taxNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.org_number
     *
     * @return the value of neuedu_seller.org_number
     *
     * @mbg.generated
     */
    public String getOrgNumber() {
        return orgNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.org_number
     *
     * @param orgNumber the value for neuedu_seller.org_number
     *
     * @mbg.generated
     */
    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber == null ? null : orgNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.address
     *
     * @return the value of neuedu_seller.address
     *
     * @mbg.generated
     */
    public Long getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.address
     *
     * @param address the value for neuedu_seller.address
     *
     * @mbg.generated
     */
    public void setAddress(Long address) {
        this.address = address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.logo_pic
     *
     * @return the value of neuedu_seller.logo_pic
     *
     * @mbg.generated
     */
    public String getLogoPic() {
        return logoPic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.logo_pic
     *
     * @param logoPic the value for neuedu_seller.logo_pic
     *
     * @mbg.generated
     */
    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic == null ? null : logoPic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.brief
     *
     * @return the value of neuedu_seller.brief
     *
     * @mbg.generated
     */
    public String getBrief() {
        return brief;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.brief
     *
     * @param brief the value for neuedu_seller.brief
     *
     * @mbg.generated
     */
    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.create_time
     *
     * @return the value of neuedu_seller.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.create_time
     *
     * @param createTime the value for neuedu_seller.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.legal_person
     *
     * @return the value of neuedu_seller.legal_person
     *
     * @mbg.generated
     */
    public String getLegalPerson() {
        return legalPerson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.legal_person
     *
     * @param legalPerson the value for neuedu_seller.legal_person
     *
     * @mbg.generated
     */
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.legal_person_card_id
     *
     * @return the value of neuedu_seller.legal_person_card_id
     *
     * @mbg.generated
     */
    public String getLegalPersonCardId() {
        return legalPersonCardId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.legal_person_card_id
     *
     * @param legalPersonCardId the value for neuedu_seller.legal_person_card_id
     *
     * @mbg.generated
     */
    public void setLegalPersonCardId(String legalPersonCardId) {
        this.legalPersonCardId = legalPersonCardId == null ? null : legalPersonCardId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.bank_user
     *
     * @return the value of neuedu_seller.bank_user
     *
     * @mbg.generated
     */
    public String getBankUser() {
        return bankUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.bank_user
     *
     * @param bankUser the value for neuedu_seller.bank_user
     *
     * @mbg.generated
     */
    public void setBankUser(String bankUser) {
        this.bankUser = bankUser == null ? null : bankUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.bank_name
     *
     * @return the value of neuedu_seller.bank_name
     *
     * @mbg.generated
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.bank_name
     *
     * @param bankName the value for neuedu_seller.bank_name
     *
     * @mbg.generated
     */
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.question
     *
     * @return the value of neuedu_seller.question
     *
     * @mbg.generated
     */
    public String getQuestion() {
        return question;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.question
     *
     * @param question the value for neuedu_seller.question
     *
     * @mbg.generated
     */
    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.answer
     *
     * @return the value of neuedu_seller.answer
     *
     * @mbg.generated
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.answer
     *
     * @param answer the value for neuedu_seller.answer
     *
     * @mbg.generated
     */
    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column neuedu_seller.update_time
     *
     * @return the value of neuedu_seller.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column neuedu_seller.update_time
     *
     * @param updateTime the value for neuedu_seller.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_seller
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sellerId=").append(sellerId);
        sb.append(", name=").append(name);
        sb.append(", nickName=").append(nickName);
        sb.append(", password=").append(password);
        sb.append(", email=").append(email);
        sb.append(", mobile=").append(mobile);
        sb.append(", telephone=").append(telephone);
        sb.append(", status=").append(status);
        sb.append(", addressDetail=").append(addressDetail);
        sb.append(", linkmanName=").append(linkmanName);
        sb.append(", linkmanQq=").append(linkmanQq);
        sb.append(", linkmanMobile=").append(linkmanMobile);
        sb.append(", linkmanEmail=").append(linkmanEmail);
        sb.append(", licenseNumber=").append(licenseNumber);
        sb.append(", taxNumber=").append(taxNumber);
        sb.append(", orgNumber=").append(orgNumber);
        sb.append(", address=").append(address);
        sb.append(", logoPic=").append(logoPic);
        sb.append(", brief=").append(brief);
        sb.append(", createTime=").append(createTime);
        sb.append(", legalPerson=").append(legalPerson);
        sb.append(", legalPersonCardId=").append(legalPersonCardId);
        sb.append(", bankUser=").append(bankUser);
        sb.append(", bankName=").append(bankName);
        sb.append(", question=").append(question);
        sb.append(", answer=").append(answer);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_seller
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Seller other = (Seller) that;
        return (this.getSellerId() == null ? other.getSellerId() == null : this.getSellerId().equals(other.getSellerId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getNickName() == null ? other.getNickName() == null : this.getNickName().equals(other.getNickName()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getTelephone() == null ? other.getTelephone() == null : this.getTelephone().equals(other.getTelephone()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getAddressDetail() == null ? other.getAddressDetail() == null : this.getAddressDetail().equals(other.getAddressDetail()))
            && (this.getLinkmanName() == null ? other.getLinkmanName() == null : this.getLinkmanName().equals(other.getLinkmanName()))
            && (this.getLinkmanQq() == null ? other.getLinkmanQq() == null : this.getLinkmanQq().equals(other.getLinkmanQq()))
            && (this.getLinkmanMobile() == null ? other.getLinkmanMobile() == null : this.getLinkmanMobile().equals(other.getLinkmanMobile()))
            && (this.getLinkmanEmail() == null ? other.getLinkmanEmail() == null : this.getLinkmanEmail().equals(other.getLinkmanEmail()))
            && (this.getLicenseNumber() == null ? other.getLicenseNumber() == null : this.getLicenseNumber().equals(other.getLicenseNumber()))
            && (this.getTaxNumber() == null ? other.getTaxNumber() == null : this.getTaxNumber().equals(other.getTaxNumber()))
            && (this.getOrgNumber() == null ? other.getOrgNumber() == null : this.getOrgNumber().equals(other.getOrgNumber()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getLogoPic() == null ? other.getLogoPic() == null : this.getLogoPic().equals(other.getLogoPic()))
            && (this.getBrief() == null ? other.getBrief() == null : this.getBrief().equals(other.getBrief()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getLegalPerson() == null ? other.getLegalPerson() == null : this.getLegalPerson().equals(other.getLegalPerson()))
            && (this.getLegalPersonCardId() == null ? other.getLegalPersonCardId() == null : this.getLegalPersonCardId().equals(other.getLegalPersonCardId()))
            && (this.getBankUser() == null ? other.getBankUser() == null : this.getBankUser().equals(other.getBankUser()))
            && (this.getBankName() == null ? other.getBankName() == null : this.getBankName().equals(other.getBankName()))
            && (this.getQuestion() == null ? other.getQuestion() == null : this.getQuestion().equals(other.getQuestion()))
            && (this.getAnswer() == null ? other.getAnswer() == null : this.getAnswer().equals(other.getAnswer()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_seller
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSellerId() == null) ? 0 : getSellerId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getNickName() == null) ? 0 : getNickName().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getTelephone() == null) ? 0 : getTelephone().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getAddressDetail() == null) ? 0 : getAddressDetail().hashCode());
        result = prime * result + ((getLinkmanName() == null) ? 0 : getLinkmanName().hashCode());
        result = prime * result + ((getLinkmanQq() == null) ? 0 : getLinkmanQq().hashCode());
        result = prime * result + ((getLinkmanMobile() == null) ? 0 : getLinkmanMobile().hashCode());
        result = prime * result + ((getLinkmanEmail() == null) ? 0 : getLinkmanEmail().hashCode());
        result = prime * result + ((getLicenseNumber() == null) ? 0 : getLicenseNumber().hashCode());
        result = prime * result + ((getTaxNumber() == null) ? 0 : getTaxNumber().hashCode());
        result = prime * result + ((getOrgNumber() == null) ? 0 : getOrgNumber().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getLogoPic() == null) ? 0 : getLogoPic().hashCode());
        result = prime * result + ((getBrief() == null) ? 0 : getBrief().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getLegalPerson() == null) ? 0 : getLegalPerson().hashCode());
        result = prime * result + ((getLegalPersonCardId() == null) ? 0 : getLegalPersonCardId().hashCode());
        result = prime * result + ((getBankUser() == null) ? 0 : getBankUser().hashCode());
        result = prime * result + ((getBankName() == null) ? 0 : getBankName().hashCode());
        result = prime * result + ((getQuestion() == null) ? 0 : getQuestion().hashCode());
        result = prime * result + ((getAnswer() == null) ? 0 : getAnswer().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}