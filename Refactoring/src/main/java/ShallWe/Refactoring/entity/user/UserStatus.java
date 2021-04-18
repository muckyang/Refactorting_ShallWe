package ShallWe.Refactoring.entity.user;

import ShallWe.Refactoring.entity.BaseEnum;

public enum UserStatus {
    ACTIVE, BAN;

    public boolean contains(String status){
        for(Enum e : UserStatus.values()){
            if(e.toString().equals(status.toUpperCase()))
                return true;
        }
        return false;
    }
}