// Code goes here

(function () {
  var app = angular.module('todo', []);
  
  app.controller('TodoCtrl', ['$scope', function($scope) {
    // $scope.name = 'Chris';
    
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
     ]
  }]);
})();