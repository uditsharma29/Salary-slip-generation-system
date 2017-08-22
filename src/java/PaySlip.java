/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aticus
 */
public class PaySlip 
{
    private String name;
    private Double basic;
    private Double agp;
    private String department;
    private Double actualBasic;
    private Double da;
    private static Double daPercentage;
    private Double hra;
    private Double ma;
    private Double total;
    private Double pfDeduction;
    private static Double pfDeductionPercentage;
    private Double instPF;
    private Double pfLoan;
    private Double houseRentDeduction;
    private Double pTax;
    private String email;
    private Double iTax;
    private Double NetPay;
    private String employeeId;
    private String payscaleFrom;
    private String payscaleTo;
    private static Double hraPercentage;
    private Double totalDeduction;
    private String month;
    private String session;
    private String Designation;
    private Double LWP;
    private Double welfarefund;

    public Double getDaPercentage() {
        return daPercentage;
    }

    public void setDaPercentage(Double daPercentage) {
        this.daPercentage = daPercentage;
    }

    public void setPfDeductionPercentage(Double pfDeductionPercentage) {
        this.pfDeductionPercentage = pfDeductionPercentage;
    }

    public void setHraPercentage(Double hraPercentage) {
        this.hraPercentage = hraPercentage;
    }

    public Double getPfDeductionPercentage() {
        return pfDeductionPercentage;
    }

    public Double getHraPercentage() {
        return hraPercentage;
    }
    

    public void setDesignation(String Designation) {
        this.Designation = Designation;
    }

    public String getDesignation() {
        return Designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getLWP() {
        return LWP;
    }

    public Double getWelfarefund() {
        return welfarefund;
    }
    

    
    public String getMonth() {
        return month;
    }

    public String getSession() {
        return session;
    }

    public void setLWP(Double LWP) {
        this.LWP = LWP;
    }

    public void setWelfarefund(Double welfarefund) {
        this.welfarefund = welfarefund;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setMonth(String month) {
        this.month = month;
    }
    
    

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    
    public String getName() {
        return name;
    }

    public Double getBasic() {
        return basic;
    }

    public Double getAgp() {
        return agp;
    }

    public Double getActualBasic() {
        return actualBasic;
    }

    public Double getDa() {
        return da;
    }

    public Double getHra() {
        return hra;
    }

    public Double getMa() {
        return ma;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getTotal() {
        return total;
    }

    public String getPayscaleFrom() {
        return payscaleFrom;
    }

    public String getPayscaleTo() {
        return payscaleTo;
    }

    public void setPayscaleFrom(String payscaleFrom) {
        this.payscaleFrom = payscaleFrom;
    }

    public void setPayscaleTo(String payscaleTo) {
        this.payscaleTo = payscaleTo;
    }

    public Double getPfDeduction() {
        return pfDeduction;
    }

    public Double getInstPF() {
        return instPF;
    }

    public Double getPfLoan() {
        return pfLoan;
    }

    public Double getHouseRentDeduction() {
        return houseRentDeduction;
    }

    public Double getpTax() {
        return pTax;
    }

    public Double getiTax() {
        return iTax;
    }

    public Double getNetPay() {
        return NetPay;
    }

    public Double getTotalDeduction() {
        return totalDeduction;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBasic(Double basic) {
        this.basic = basic;
    }

    public void setAgp(Double agp) {
        this.agp = agp;
    }

    public void setActualBasic(Double actualBasic) {
        this.actualBasic = actualBasic;
    }

    public void setDa(Double da) {
        this.da = da;
    }

    public void setHra(Double hra) {
        this.hra = hra;
    }

    public void setMa(Double ma) {
        this.ma = ma;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setPfDeduction(Double pfDeduction) {
        this.pfDeduction = pfDeduction;
    }

    public void setInstPF(Double instPF) {
        this.instPF = instPF;
    }

    public void setPfLoan(Double pfLoan) {
        this.pfLoan = pfLoan;
    }

    public void setHouseRentDeduction(Double houseRentDeduction) {
        this.houseRentDeduction = houseRentDeduction;
    }

    public void setpTax(Double pTax) {
        this.pTax = pTax;
    }

    public String getDepartment() {
        return department;
    }
    
    

    public void setiTax(Double iTax) {
        this.iTax = iTax;
    }

    public void setNetPay(Double NetPay) {
        this.NetPay = NetPay;
    }

    public void setTotalDeduction() {
        this.totalDeduction = LWP+pfDeduction+pfLoan+houseRentDeduction+pTax+iTax+welfarefund;
    }
    
    
    public String getAmountInWords()
    {
        return EnglishNumberToWords.convert(NetPay.longValue());
    }
        
}
