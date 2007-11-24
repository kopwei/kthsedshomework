
cd build\classes
rmic market.client.ClientImpl
rmic market.server.ClientAccountImpl
rmic market.server.ItemForSellImpl
rmic market.server.MarketServerImpl
rmic bank.BankImpl
rmic bank.BankAccountImpl

start rmiregistry