//TankDuino o ArduTank
//código creado por mi (acidoBinario) usando información de muuuuchas fuentes, pero mas en la app de android.
// este codigo es Open Source, así que pueden usarlo como quieran y compartirlo y hacer sus propias cosas :D
// version 5 
//
#include <Servo.h>
#include <SoftwareSerial.h>

//declarar BT y SS
SoftwareSerial mySerial(0, 1);// RXD, TXD
int dataFromBT = 1;
int temp;

//declarar los pines de motoresy servo :v
#define m1a 8
#define m1b 9
#define m2a 10
#define m2b 11
#define s 4

// valores pal servo
int sv = 30;
int tv;

Servo tor;

void setup() {
  
  Serial.begin(57600);

  // esta parte es para que quede lindo como se ve el monitor serial xD
  Serial.println("inicializando TANK control!");
  Serial.println("by Federico Videla ©");
  Serial.print("T");
  delay(100);
  Serial.print("A");
  delay(100);
  Serial.print("N");
  delay(100);
  Serial.print("K");
  delay(100);
  Serial.print(" ");
    delay(100);
  Serial.print("c");
    delay(100);
  Serial.print("o");
    delay(100);
  Serial.print("n");
    delay(100);
  Serial.print("t");
    delay(100);
  Serial.print("r");
    delay(100);
  Serial.print("o");
    delay(100);
  Serial.print("l");
    delay(100);
  Serial.println("!!");
    delay(100);
  // fin de la parte del monitor serial

  // my serial para obtener los datos del modulo BT
  mySerial.begin(9600);

  // servo de torreta
  tor.attach(s);
  
  //modos de pines de los motores
  pinMode(13, OUTPUT); 
  pinMode(m1a, OUTPUT); 
  pinMode(m1b, OUTPUT); 
  pinMode(m2a, OUTPUT); 
  pinMode(m2b, OUTPUT); 
}
 
void loop() {
  //función principal que compara los resultados obtenidos de myserial para hacer algo :D
  comparar();
}

void comparar(){
  //checkea que este disponible el modulo bt y guarda los datos recibidos en dataFromBT
  if (mySerial.available()){
     dataFromBT = mySerial.read();
     //Serial.println(dataFromBT);
  }
  //comparación de resultados, podría haber usado un switch case pero ya lo hice así q ggwp, igual funciona
  if (dataFromBT == '0') {
    // adelante
    adelante();
    digitalWrite(13, HIGH);
    delay(100);
    digitalWrite(13, LOW);
    Serial.println("adelante");
  } else if (dataFromBT == '1') {
    // atras
    atras();
    Serial.println("atras");
    digitalWrite(13, HIGH);
    delay(100);
    digitalWrite(13, LOW);
  } else if (dataFromBT == '2') {
    // inzquierda
    izquierda();
    Serial.println("izquierda");
    digitalWrite(13, HIGH);
    delay(100);
    digitalWrite(13, LOW);
  } else if (dataFromBT == '3') {
    // derecha
    derecha();
    Serial.println("derecha");
    digitalWrite(13, HIGH);
        delay(100);
    digitalWrite(13, LOW);
  } else if (dataFromBT == '4') {
    // detenerse
    detenerse();
    Serial.println("detenerse");
    digitalWrite(13, HIGH);
    delay(100);
    digitalWrite(13, LOW);  
  } else if (dataFromBT == '5'){
    if (sv <= 180){
      if (sv != 180){
    sv = sv + 1 ;
    tv = map(sv, 0, 60, 0, 180);
    Serial.print("servo : ");
    Serial.print(tv);
    Serial.print("  value: ");
    Serial.println(sv);
    torfuncion(tv);}
    } 
  } else if (dataFromBT == '6'){
    sv = sv - 1;
    if (sv >= 0){
    if (sv != 0){
    tv = map(sv, 0, 60, 0, 180);
    Serial.print("servo : ");
    Serial.print(tv);
    Serial.print("  value: ");
    Serial.println(sv);
    torfuncion(tv);
    }
    }
  } else if (dataFromBT == '7'){
    banear();
    }
  //un pequeño delay entre cada comando :D
  delay(90);
  }

//
// TODAS LAS FUNCIONES PARA CONTROLAR EL TANQUE
// estan justo abajo :D
//
void banear(){
  digitalWrite(3, 1);
  Serial.println("banea2 xDxdxD");
  }

void torfuncion(int x){
  tor.write(x);
  }

void adelante(){
  digitalWrite(m1a, LOW);
  digitalWrite(m1b, HIGH);
  digitalWrite(m2a, LOW);
  digitalWrite(m2b, HIGH);
}
void atras(){
  digitalWrite(m1a, HIGH);
  digitalWrite(m1b, LOW);
  digitalWrite(m2a, HIGH);
  digitalWrite(m2b, LOW);
}
void izquierda(){
  digitalWrite(m1a, HIGH);
  digitalWrite(m1b, LOW);
  digitalWrite(m2a, LOW);
  digitalWrite(m2b, HIGH);
}
void derecha(){
  digitalWrite(m1a, LOW);
  digitalWrite(m1b, HIGH);
  digitalWrite(m2a, HIGH);
  digitalWrite(m2b, LOW);
}
void detenerse(){
  digitalWrite(2, 0);
  digitalWrite(m1a, 0);
  digitalWrite(m1b, 0);
  digitalWrite(m2a, 0);
  digitalWrite(m2b, 0);
}

