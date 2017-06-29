//--------------------------------------------------------------------------------------
// ● 3가지 방식이 있다.
//   - service
//   - factory
//   - provider
//---------------------------------------------------------------------------------------
// ● localStorage (개발자 툴 > Application > Storage > Local Storage)
//   - Key, Value 저장소
//   - localStorage.setItem(key, value);
//   - localStorage.getItem(key); // value, localStorage[key] 접근가능
//   - 입력은 무조건 string으로 처리됨

//   - localStorage.length
//   - localStorage.key(value); //key
//   - 최대 약 5mb 용량
//   - 크롬은 SQLite 사용함
//   - 영구 보관
//   - sesstionStorage는 새 탭, 새 윈도우로 범위가 제한된다는 점이 localStorage와 차이점
//   - 참고: http://ohgyun.com/417
//---------------------------------------------------------------------------------------

angular.module('todo').controller('TodoCtrl'
                                    //, ['$scope'
                                    , function($scope, todoStorage) {
     
    
     
     //$scope.tempLogoString = 'BlockChain.NoMarginTrading.App';
     $scope.tempLogoString = 'Cryptown.eth';
     
    //  if (sessionStorage.length == '1')
    //     $scope.LoginStatus = '로그인';
    //  else
    //     $scope.LoginStatus = '로그아웃';
    
    $scope.LoginInStatus = '로그인';
    $scope.LoginStatus = '로그아웃';
    
    $scope.etherMetaMaskAccount = web3.eth.accounts[0];

     //  $scope.todo = {
     //   title: '요 가 수 련',
     //   completed: false,
     //   createdAt: Date.now()
     //  }
    
     $scope.todos = todoStorage.get();
     // $scope.todos = [
     //   {
     //     title: '요가 수련',
     //     completed: false,
     //     createdAt: Date.now()
     //   },
     //   {
     //     title: '앵귤러 학습',
     //     completed: false,
     //     createdAt: Date.now()
     //   },
     //   {
     //     title: '운동 하기',
     //     completed: true,
     //     createdAt: Date.now()
     //   }
     // ];
     
     $scope.remove = function(todo) {
        //alert("alert test");
        
        todoStorage.remove(todo);
        // find todo index in todos
        // var idx = $scope.todos.findIndex(function (item) {
        //   return item.id == todo.id;
        // })
        
        // remove from todos
        // if(idx > -1) {
        //   $scope.todos.splice(idx, 1);
        // }
      
     };
     
     $scope.add = function (newTodoTitle) {
       //alert(newTodoTitle);
       
       todoStorage.add(newTodoTitle);
       // create new todo
       // var newTodo = {
       //   title: newTodoTitle,
       //   completed: false,
       //   createAt: Date.now()
       // };
       
       // push into todos
       //$scope.todos.push(newTodo);
       
       //empty form
       $scope.newTodoTitle = "";
     };
     
     $scope.update = function () {
       todoStorage.update();
     }
     
     $scope.login = function (loginInfo) {
       todoStorage.login(loginInfo);
     }
     
     $scope.logup = function (userInfo) {
       todoStorage.logup(userInfo);
     }
}
//]
);