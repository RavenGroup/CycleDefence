#include <stm32f10x.h>

int main(void) {
	
	RCC->AHBENR |= GPIO_BRR_BR0;
	
	GPIOC ->CRH = 0x50000;
	
	GPIOC->CRL = 0;
	
	GPIOC->ODR = 0;
	
	while(1)
	{
		GPIOC->ODR = 0x100;
		for (int i=0; i<500000; i++){}
			
		GPIOC->ODR = 0x200;
			
		for (int i=0; i<500000; i++){}
	}		
}