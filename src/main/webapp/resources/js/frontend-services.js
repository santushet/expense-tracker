/**
 * 
 */

angular.module("frontendServices", []).service('ExpenseService',
		[ '$http', '$q', function($http, $q) {
			return {
				searchExpenses : function(fromDate, toDate, pageNumber) {
					var deferred = $q.defer();

					$http.get('/expense', {
						params : {
							fromDate : fromDate,
							toDate : toDate,
							pageNumber : pageNumber
						}
					}).then(function(response) {
						if (response.status == 200) {
							deferred.resolve(response.data);
						} else {
							deferred.reject('Error retrieving expenses');
						}
					});
					return deferred.promise;
				},
				   deleteExpenses: function(deletedExpenseIds) {
		                var deferred = $q.defer();

		                $http({
		                    method: 'DELETE',
		                    url: '/expense',
		                    data: deletedExpenseIds,
		                    headers: {
		                        "Content-Type": "application/json"
		                    }
		                })
		                .then(function (response) {
		                    if (response.status == 200) {
		                        deferred.resolve();
		                    }
		                    else {
		                        deferred.reject('Error deleting expenses');
		                    }
		                });

		                return deferred.promise;
		            },

				saveExpenses : function(expenses) {
					var deferred = $q.defer();

					$http({
						method : "POST",
						data : expenses,
						url : '/expense',
						content : {
							"Content-Type" : "application/json",
							"Accept" : "text/plain, application/json"
						}
					}).then(function(response) {
						if (response.status == 200) {
							deferred.resolve();
						} else {
							deferred.reject("failed to save expenses");
						}
					});
					return deferred.promise;
				}
			}
		} ]).service('UserService', [ '$http', '$q', function($http, $q) {
	return {
		getUserInfo : function() {
			var deferred = $q.defer();

			$http.get('/user').then(function(response) {
				if (response.status == 200) {
					deferred.resolve(response.data);
				} else {
					deferred.reject("user information not available");
				}
			});
			return deferred.promise;
		},
		logout : function() {
			$http({
				method : 'POST',
				url : '/logout'
			}).then(function(response) {
				if (response.status == 200) {
					window.location.reload();

				} else {
					console.log("Logout failed");
				}
			});
		}
	};
} ]);
