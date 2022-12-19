//+------------------------------------------------------------------+
//|                                                         frog.mq4 |
//|                        Copyright 2022, MetaQuotes Software Corp. |
//|                                             https://www.mql5.com |
//+------------------------------------------------------------------+
#property copyright "Copyright 2022, MetaQuotes Software Corp."
#property link      "https://www.mql5.com"
#property version   "1.00"
#property strict

extern bool patternActivity = false;
extern bool patternPassivity = false;
extern int checkCountTick = 10;

string URL="http://localhost/api/v1/ea/signal";

int countTick, res;
char post[];
char result[];
string headers;

//+------------------------------------------------------------------+
//| Expert initialization function                                   |
//+------------------------------------------------------------------+
int OnInit()
{
//--- create timer

//---
   return(INIT_SUCCEEDED);
}
//+------------------------------------------------------------------+
//| Expert deinitialization function                                 |
//+------------------------------------------------------------------+
void OnDeinit(const int reason)
{
//--- destroy timer

}
//+------------------------------------------------------------------+
//| Expert tick function                                             |
//+------------------------------------------------------------------+
void OnTick()
{

   if (countTick >= checkCountTick) {
      if(patternActivity) postPrice("activity");
      if(patternActivity) postPrice("passivity");
   }

}

//+------------------------------------------------------------------+
void createCross(color colors){

  string nameLine = TimeToString(TimeCurrent(), TIME_DATE | TIME_MINUTES | TIME_SECONDS);
  nameLine += " ";
  nameLine += colors;
  ObjectCreate(nameLine, OBJ_ARROW, 0, TimeCurrent(), Bid);
  ObjectSetInteger(0,nameLine,OBJPROP_ARROWCODE,251);// установим код стрелки
  ObjectSetInteger(0, nameLine, OBJPROP_COLOR, colors);

}
//+------------------------------------------------------------------+
void createCircle(color colors){

  string nameLine = TimeToString(TimeCurrent(), TIME_DATE | TIME_MINUTES | TIME_SECONDS);
  nameLine += " ";
  nameLine += colors;
  ObjectCreate(nameLine, OBJ_ARROW, 0, TimeCurrent(), Bid);
  ObjectSetInteger(0,nameLine,OBJPROP_ARROWCODE,161);// установим код круга
  ObjectSetInteger(0, nameLine, OBJPROP_COLOR, colors);

}
//+------------------------------------------------------------------+
void createLine(color colors){

  string nameLine = TimeToString(TimeCurrent(), TIME_DATE | TIME_MINUTES);
  ObjectCreate(nameLine, OBJ_HLINE, 0, 0, Bid);
  ObjectSetInteger(0, nameLine, OBJPROP_COLOR, colors);

}
//+------------------------------------------------------------------+
void postPrice(string pattern){
   string url = URL
   + "?priceAsk=" + DoubleToString(Ask)
   + "&priceBid=" + DoubleToString(Bid)
   + "&pattern=" + pattern;

      res = WebRequest("POST",url,NULL,NULL,500,post,0,result,headers);
      if (res == 300) {
         createCircle(clrWhite);
      }
      if(res == 200){//если прошла дельта по аску
         createCross(clrRed);
         return;
      }
      if(res == 201){//если прошла дельта по бид
        createCross(clrBlue);
        return;
      }
      if(res == 202){//если прошли обе дельты цен
        createCross(clrGold);
        return;
      }
      if(res == -1) {
         Print("Ошибка в WebRequest. Код ошибки2  =",GetLastError());
      }
      if(res == 500){
        Print("Ошибка сервера");
      }
      if(res == 400){
        Print("Выборка слишком мала");
        return;
      }

}
//+------------------------------------------------------------------+