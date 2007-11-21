@echo off
cd build\classes
rmic market.server.ClientAccountImpl
rmic market.server.ItemForSellImpl
rmic market.server.MarketServerImpl
@echo on
start rmiregistry