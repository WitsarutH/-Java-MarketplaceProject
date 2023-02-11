package ku.cs.shop.models;

import java.util.ArrayList;

public class MemberAccountList {
    private ArrayList<MemberAccount> memberAccounts;

    public MemberAccountList() {
        // ใช้ new เพื่อสร้าง instance ของ ArrayList
        memberAccounts = new ArrayList<>();
    }

    public void addMemberAccount(MemberAccount account) {
        // เรียก method add จาก ArrayList เพื่อเพิ่มข้อมูล
        memberAccounts.add(account);
    }
    public void addToFront(MemberAccount account){
        memberAccounts.add(0,account);
    }
    public void removeMemberAccount(MemberAccount account){
        memberAccounts.remove(memberAccounts.indexOf(account));
    }

    public boolean checkLogin(String username, String password) {
        for (MemberAccount account : memberAccounts) {
            if (account.checkUsername(username)) {
                if (account.checkPassword(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    public MemberAccount checkMemberAccount(String username) {
        for (MemberAccount account : memberAccounts) {
            if (account.checkUsername(username)) {
                    return account;
                }
            }
        return null;
    }
    public boolean checkUsernameEqual(String username){
        for (MemberAccount account : memberAccounts) {
            if (account.checkUsername(username)) {
                    return false;
                }
            }
        return true;
    }

    public ArrayList<MemberAccount> getAllAccounts(){
        return memberAccounts;
    }

    public String toCsv(){
        String result = "";
        for(MemberAccount memberAccount : this.memberAccounts){
            result += memberAccount.toCsv() + "\n";
        }
        return result;
    }
}