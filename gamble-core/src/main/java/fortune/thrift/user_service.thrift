namespace java fortune.thrift

service  UserService {
  bool deposit(1:string userid, 2:double account)
}