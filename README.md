# CSC207Projectum

### Entity
- Asset: "The financial all the financial assets"
    - #### instance attributes:
      name: "The name of the assets"

    -  #### method:
       getters & setters


- Stock extends Asset: "Stock of companies"
    - #### instance attributes:
      stockSymbol: "The stock symbol of the stocks"

    - #### method:
      getters & setters

- StockInPortfolio extends Stock: "The stock exists in the portfolio"
    - #### instance attributes:
      averagePrice: "The average price that the user of this software paid the entire shares of the stock"

      numberOfShares: "The total number of shares of the stock"

    - #### method:
      getters & setters

      lookForPrice: "Check the price of the stock"

- Portfolio
    - #### instance attributes:
      stocksInPortfolio: "The list of all the stocks that exist in the portfolio"

    - #### method:
      getters & setters

### UserCase
- ModelPredict: CD
- BackTest: SD
- CalculateRevenue: Whole team
- AddStockAtGivenPrice: MW
- RemoveStock: SD
- ClearAll: CD



