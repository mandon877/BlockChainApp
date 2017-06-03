// 3가지 방식이 있다.
// service
// factory
// provider
angular.module('todo').factory('todoStorage', function ($http, $window, $rootScope) {
  var TODO_DATA = 'TODO_DATA';
  var tempUser = '';
  var tempPw = '';
  

  var storage = {
     todos: [],
     //  todos : [
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
     //  ],
    
     userprofiles: [
       {
         userId: 'mandon877@gmail.com',
         password: '1234567891'
       },
       {
         userId: 'mandon877@hotmail.com',
         password: '1234567891'
       }
     ],
    
     _saveToLocalStorage: function (data) {
       localStorage.setItem(TODO_DATA, JSON.stringify(data))
     },
     
     _getFromLocalStorage: function () {
       return JSON.parse(localStorage.getItem(TODO_DATA)) || [];
     },
     
     get: function () {
       //storage.todos = storage._getFromLocalStorage()
       angular.copy(storage._getFromLocalStorage(), storage.todos)
       return storage.todos;
     },
     
     remove: function (todo) {
        var idx = storage.todos.findIndex(function (item) {
          return item.id == todo.id;
        })
        
        // remove from todos
        if(idx > -1) {
          //remove
          storage.todos.splice(idx, 1);
          
          //save Local Storage
          storage._saveToLocalStorage(storage.todos);
        }
     },
     
     add: function (newTodoTitle) {
        // create new todo
        var newTodo = {
          title: newTodoTitle,
          completed: false,
          createdAt: Date.now()
        };
       
       // push into todos
       storage.todos.push(newTodo);
       
       //save LocalStorage 
       storage._saveToLocalStorage(storage.todos);
     },
     
     update: function () {
         storage._saveToLocalStorage(storage.todos);
     },
     
     //////////////
     // login 처리
     //////////////
     login: function (loginInfo) {
       //console.log('here')
       return fetch('/login', {
           method: 'POST',
           headers: {
             "content-type": "application/json"
           },
           body: JSON.stringify(loginInfo)
       })
       .then(res => res.json())
       .then(json => console.log(json))
       .catch(err => {
           throw new Error('Error login:', err)
        });
       // find user information
       //   var user_idx = storage.userprofiles.findIndex(function (user_info) {
       //       return user_info.userId == loginInfo.userId && user_info.password == loginInfo.password
       //   })

       // 권한 부여
    //   if(user_idx > -1) {
    //         //alert("#111");
    //         $window.sessionStorage["loginInfo"] = JSON.stringify(loginInfo);
            
    //         //tempUser = JSON.parse(sessionStorage.loginInfo);
    //         //  $rootScope.userId = tempUser.userId;
    //         //  $rootScope.password = tempUser.password;
    //         //$window.location.href = '/login/' + tempUser.userId + '/' + tempUser.password + ''; 
            
    //         $rootScope.loggedIn = true;
    //         $window.location.href = '/login/' + $rootScope.loggedIn; 
    //   }
     }
     
  }

  return storage;
});