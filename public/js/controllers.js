angular.module('todo').controller('TodoCtrl', ['$scope', function($scope) {
     $scope.tempLogoString = 'BlockChain.NoMarginTrading.App [정다은의 집]';
    
    //  $scope.todo = {
    //   title: '요 가 수 련',
    //   completed: false,
    //   createdAt: Date.now()
    //  }
    
    $scope.todos = [
      {
        title: '요가 수련',
        completed: false,
        createdAt: Date.now()
      },
      {
        title: '앵귤러 학습',
        completed: false,
        createdAt: Date.now()
      },
      {
        title: '운동 하기',
        completed: true,
        createdAt: Date.now()
      }
     ];
     
     $scope.remove = function(todo) {
        //alert("alert test");
        
        // find todo index in todos
        var idx = $scope.todos.findIndex(function (item) {
          return item.id == todo.id;
        })
        
        // remove from todos
        if(idx > -1) {
          $scope.todos.splice(idx, 1);
        }
      
     };
     
     $scope.add = function (newTodoTitle) {
       //alert(newTodoTitle);
       
       // create new todo
       var newTodo = {
         title: newTodoTitle,
         completed: false,
         createAt: Date.now()
       };
       
       // push into todos
       $scope.todos.push(newTodo);
       
       //empty form
       $scope.newTodoTitle = "";
     }
   }]);