namespace java thrift.server  // defines the namespace
   
typedef i32 int  //typedefs to get convenient names for your types 
   
service thrift.server.AdditionService {  // defines the service to add two numbers
        int add(1:int n1, 2:int n2), //defines a method 
}