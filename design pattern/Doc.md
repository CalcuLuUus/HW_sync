# 设计模式作业01：虚拟钱包的设计与实现

## 1. 贫血设计
贫血钱包主要是将钱包主体和业务分离。钱包中只包含了基本信息，不包含业务逻辑，钱包类设计代码如下：
```java
// wallet\walletADM\VirtualWalletBo.java
// 省略getter，setter和constructor
public class VirtualWalletBo {
    private Long id;
    private Long createTime;
    private BigDecimal balance;
}
```
业务层单独写一个文件，并且把业务的逻辑一并实现
```java
// wallet\walletADM\VirtualWalletService.java
// 省略创建钱包，获取钱包实体，获取余额的实现
public class VirtualWalletService {

    public boolean debit(Long walletId, BigDecimal amount, ArrayList<VirtualWalletBo> wallets){
        boolean flg = true;
        try {
            VirtualWalletBo virtualWalletBo = getVirtualWallet(walletId, wallets);
            if(virtualWalletBo == null) return false;
            BigDecimal balance = getBalance(walletId, wallets);
            if(balance.compareTo(amount) < 0){
                flg = false;
                throw new NoSufficientBalanceException();
            }
            System.out.println("Successfully!");
            virtualWalletBo.setBalance(balance.subtract(amount));
        } catch (NoSufficientBalanceException e) {
        }
        return flg;
    }

    public void credit(Long walletId, BigDecimal amount, ArrayList<VirtualWalletBo> wallets){
        VirtualWalletBo virtualWalletBo = getVirtualWallet(walletId, wallets);
        if(virtualWalletBo == null) return;
        System.out.println("Successfully!");
        BigDecimal balance = getBalance(walletId, wallets);
        virtualWalletBo.setBalance(balance.add(amount));
    }

    public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount, ArrayList<VirtualWalletBo> wallets){
        VirtualWalletBo fromWallet = getVirtualWallet(fromWalletId, wallets);
        if(fromWallet == null){
            return ;
        }
        VirtualWalletBo toWallet = getVirtualWallet(toWalletId, wallets);
        if(toWallet == null){
            return ;
        }
        boolean flg = debit(fromWalletId, amount, wallets);
        if(!flg) return ; // fail debit
        credit(toWalletId, amount, wallets);
        System.out.println("Successfully!");
    }

}
```

## 2. 充血设计
充血模型中，钱包的设计除了基本的信息，也包括基础的逻辑实现，代码如下
```java
// wallet\walletRDM\VirtualWalletBo.java
// 省略getter，setter和constructor
public class VirtualWalletBo {
    private Long id;
    private Long createTime;
    private BigDecimal balance;

    public VirtualWalletBo(Long id) {
        Date date = new Date();
        Long Time = date.getTime();
        this.id = id;
        createTime = Time;
        balance = BigDecimal.ZERO;
    }

    public void debit(BigDecimal amount) throws NoSufficientBalanceException {
        if(this.balance.compareTo(amount) < 0){
            throw new NoSufficientBalanceException();
        }
        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) throws InvalidAmountException {
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new InvalidAmountException();
        }
        this.balance = this.balance.add(amount);
    }
}
```
而业务层则是简单的调用实体类实现好的接口来完成相应功能，较为复杂的业务逻辑则是在实体类实现好的功能的基础上进一步实现，代码如下
```java
// walletRDM\VirtualWalletService.java
// 省略创建钱包，获取钱包实体，获取余额的实现
public class VirtualWalletService {

    public void debit(Long walletId, BigDecimal amount, ArrayList<VirtualWalletBo> wallets){
        try {
            VirtualWalletBo virtualWalletBo = getVirtualWallet(walletId, wallets);
            if(virtualWalletBo == null) return;
            virtualWalletBo.debit(amount);
            System.out.println("Successfully!");
        } catch (NoSufficientBalanceException e) {
        }
    }

    public void credit(Long walletId, BigDecimal amount, ArrayList<VirtualWalletBo> wallets){
        try{
            VirtualWalletBo virtualWalletBo = getVirtualWallet(walletId, wallets);
            if(virtualWalletBo == null) return;
            virtualWalletBo.credit(amount);
            System.out.println("Successfully!");
        }catch (InvalidAmountException e) {
        }

    }

    public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount, ArrayList<VirtualWalletBo> wallets){
        VirtualWalletBo fromWallet = getVirtualWallet(fromWalletId, wallets);
        if(fromWallet == null){
            return ;
        }
        VirtualWalletBo toWallet = getVirtualWallet(toWalletId, wallets);
        if(toWallet == null){
            return ;
        }
        try{
            fromWallet.debit(amount);
            toWallet.credit(amount);
            System.out.println("Successfully!");
        } catch (NoSufficientBalanceException | InvalidAmountException e) {

        }
    }

}
```
