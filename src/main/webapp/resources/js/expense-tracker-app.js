angular.module('expenseTrackerApp', ['editableTableWidgets', 'frontendServices', 'spring-security-csrf-token-interceptor'])
    .filter('excludeDeleted', function () {
        return function (input) {
            return _.filter(input, function (item) {
                return item.deleted == undefined || !item.deleted;
            });
        }
    })
		.controller(
				'ExpenseTrackerCtrl',
				[
						'$scope',
						'ExpenseService',
						'UserService',
						'$timeout',
						function($scope, ExpenseService, UserService, $timeout) {
							$scope.vm = {
								currentPage : 1,
								totalPages : 0,
								originalExpenses : [],
								expenses : [],
								isSelectionEmpty : true,
								errorMessages : [],
								infoMessages : []
							};
							updateUserInfo();
							loadExpenseData(null,null,1);
							
							 function showErrorMessage(errorMessage) {
					                clearMessages();
					                $scope.vm.errorMessages.push({description: errorMessage});
					            }

							function updateUserInfo() {
								UserService
										.getUserInfo()
										.then(
												function(userInfo) {
													showInfoMessage(userInfo.userName);
													showInfoMessage(userInfo.todaysExpenses);
													$scope.vm.userName = userInfo.userName;
													$scope.vm.todaysExpenses = userInfo.todaysExpenses ? userInfo.todaysExpenses
															: 'None';
												},
												function(errorMessage) {
													showErrorMessage(errorMessage);

												});
							}
							

							 function markAppAsInitialized() {
					                if ($scope.vm.appReady == undefined) {
					                    $scope.vm.appReady = true;
					                }
					            }
							 
							function loadExpenseData(fromDate,toDate,pageNumber){
							ExpenseService.searchExpenses(fromDate,toDate,pageNumber)
							.then(function(data){
								$scope.vm.errorMessages=[];
								$scope.vm.currentPage=data.currentPage;
								$scope.vm.totalPages=data.totalPages;
								 $scope.vm.originalExpenses = data.expenses;
								    $scope.vm.expenses = _.cloneDeep($scope.vm.originalExpenses);
								    _.each($scope.vm.expenses, function (expense) {
								    	expense.selected = false;
			                        });
								    markAppAsInitialized();
			                        if ($scope.vm.expenses && $scope.vm.expenses.length == 0) {
			                            showInfoMessage("No results found.");
			                        }
		                    },
		                    function (errorMessage) {
		                        showErrorMessage(errorMessage);
		                        markAppAsInitialized();

							});
							}
							 function showInfoMessage(infoMessage) {
					                $scope.vm.infoMessages = [];
					                $scope.vm.infoMessages.push({description: infoMessage});
					                $timeout(function () {
					                    $scope.vm.infoMessages = [];
					                }, 1000);
					            }
					
							  function clearMessages() {
					                $scope.vm.errorMessages = [];
					                $scope.vm.infoMessages = [];
					            }

							 
							   $scope.selectionChanged = function () {
					                $scope.vm.isSelectionEmpty = !_.any($scope.vm.expenses, function (expense) {
					                    return expense.selected && !expense.deleted;
					                });
					            };

					            $scope.pages = function () {
					                return _.range(1, $scope.vm.totalPages + 1);
					            };

					            $scope.search = function (page) {

					                var fromDate = new Date($scope.vm.fromDate);
					                var toDate = new Date($scope.vm.toDate);

					                console.log('search from ' + $scope.vm.fromDate + ' to ' + $scope.vm.toDate);

					                var errorsFound = false;

					                if ($scope.vm.fromDate && !$scope.vm.toDate || !$scope.vm.fromDate && $scope.vm.toDate) {
					                    showErrorMessage("Both from and to dates are needed");
					                    errorsFound = true;
					                    return;
					                }

					                if (fromDate > toDate) {
					                    showErrorMessage("From date cannot be larger than to date");
					                    errorsFound = true;
					                }


					                if (!errorsFound) {
					                    loadExpenseData($scope.vm.fromDate, $scope.vm.toDate, page == undefined ? 1 : page);
					                }

					            };

					            $scope.previous = function () {
					                if ($scope.vm.currentPage > 1) {
					                    $scope.vm.currentPage-= 1;
					                    loadExpenseData($scope.vm.fromDate,
					                        $scope.vm.toDate,$scope.vm.currentPage);
					                }
					            };

					            $scope.next = function () {
					                if ($scope.vm.currentPage < $scope.vm.totalPages) {
					                    $scope.vm.currentPage += 1;
					                    loadExpenseData($scope.vm.fromDate,$scope.vm.toDate,$scope.vm.currentPage);
					                }
					            };
					            $scope.goToPage = function (pageNumber) {
					                if (pageNumber > 0 && pageNumber <= $scope.vm.totalPages) {
					                    $scope.vm.currentPage = pageNumber;
					                    loadExpenseData($scope.vm.fromDate,$scope.vm.toDate,$scope.vm.currentPage);
					                }
					            };
					            $scope.add = function () {
					                $scope.vm.expenses.unshift({
					                    id: null,
					                    date: null,
					                    description: null,
					                    expenses: null,
					                    selected: false,
					                    new: true
					                });
					            };

					            $scope.delete = function () {
					                var deletedExpenseIds = _.chain($scope.vm.expenses)
					                    .filter(function (expense) {
					                        return expense.selected && !expense.new;
					                    })
					                    .map(function (expense) {
					                        return expense.id;
					                    })
					                    .value();

					                ExpenseService.deleteExpenses(deletedExpenseIds)
					                    .then(function () {
					                        clearMessages();
					                        showInfoMessage("deletion successful.");

					                        _.remove($scope.vm.expenses, function(expense) {
					                            return expense.selected;
					                        });

					                        $scope.selectionChanged();
					                        updateUserInfo();

					                    },
					                    function () {
					                        clearMessages();
					                        $scope.vm.errorMessages.push({description: "deletion failed."});
					                    });
					            };
					            
					            $scope.reset = function () {
					                $scope.vm.expenses = $scope.vm.originalExpenses;
					            };

					            function getNotNew(expenses) {
					                return  _.chain(expenses)
					                    .filter(function (expense) {
					                        return !expense.new;
					                    })
					                    .value();
					            }

					            function prepareExpensesDto(expenses) {
					                return  _.chain(expenses)
					                    .each(function (expense) {
					                        if (expense.date) {
					                            var dt = expense.date.split(" ");
					                            expense.date = dt[0];
					                           // expense.time = dt[1];
					                        }
					                    })
					                    .map(function (expense) {
					                        return {
					                            id: expense.id,
					                            date: expense.date,
					            // time: expense.time,
					                            description: expense.description,
					                            amount: expense.amount,
					                            version: expense.version
					                        }
					                    })
					                    .value();
					            }

					            $scope.save = function () {

					                var maybeDirty = prepareExpensesDto(getNotNew($scope.vm.expenses));

					                var original = prepareExpensesDto(getNotNew($scope.vm.originalExpenses));

					                var dirty = _.filter(maybeDirty).filter(function (expense) {

					                    var originalExpense = _.filter(original, function (orig) {
					                        return orig.id === expense.id;
					                    });

					                    if (originalExpense.length == 1) {
					                        originalExpense = originalExpense[0];
					                    }

					                    return originalExpense && ( originalExpense.date != expense.date ||
					                        originalExpense.time != expense.time || originalExpense.description != expense.description ||
					                        originalExpense.amount != expense.amount)
					                });

					                var newItems = _.filter($scope.vm.expenses, function (expense) {
					                    return expense.new;
					                });

					                var saveAll = prepareExpensesDto(newItems);
					                saveAll = saveAll.concat(dirty);

					                $scope.vm.errorMessages = [];

					                // save all new items plus the ones that
									// where modified
					                ExpenseService.saveExpenses(saveAll).then(function () {
					                        $scope.search($scope.vm.currentPage);
					                        showInfoMessage("Changes saved successfully");
					                        updateUserInfo();
					                    },
					                    function (errorMessage) {
					                        showErrorMessage(errorMessage);
					                    });

					            };

					            $scope.logout = function () {
					                UserService.logout();
					            }


						} ]);