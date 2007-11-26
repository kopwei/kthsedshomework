
cd build\classes
rmic market.client.ClientImpl
rmic market.server.ItemForSellImpl
rmic market.server.MarketServerImpl
rmic bank.BankImpl

start rmiregistry