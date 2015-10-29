namespace java fortune.thrift

service  DepositService {
  bool deposit(1:i32 userid, 2:double account)
}