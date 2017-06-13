
  angular.module('todo').directive('todoTitle', function() {
    return {
      template: '<h1>Todo List</h1>'
    }
  });
  
  angular.module('todo').directive('todoNavigation', function() {
    return {
      templateUrl: 'tpl/todoNavigation.tpl.html'
    }
  });
  
  angular.module('todo').directive('todoItem', function() {
    return {
      templateUrl: 'tpl/todoItem.tpl.html'
    }
  });
  
  angular.module('todo').directive('todoFilters', function() {
    return {
      templateUrl: 'tpl/todoFilters.tpl.html'
    }
  });
  
  angular.module('todo').directive('todoForm', function() {
    return {
      templateUrl: 'tpl/todoForm.tpl.html'
    }
  });
  
  angular.module('todo').directive('loginForm', function() {
    return {
      templateUrl: 'tpl/loginForm.tpl.html'
    }
  });