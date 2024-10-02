import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CustomerService } from '../../services/customer.service';
import { PlaceOrderComponent } from '../place-order/place-order.component';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.scss'
})
export class CartComponent {

  cartItems: any[] = [];
  order: any;

  constructor(
    private customerService: CustomerService,
    private snackBar: MatSnackBar,
    private fb: FormBuilder,
    public dialog: MatDialog,
  ) { }

  ngOnInit() {
    this.getCart();
  }

  getCart() {
    this.cartItems = [];
    this.customerService.getCartByUserId().subscribe(res => {
      this.order = res;
      res.cartItems.forEach(element => {
        this.cartItems.push(element);
      });
    });
  }

  increaseQuantity(productId: any) {
    this.customerService.increaseProductQuantity(productId).subscribe(res => {
      this.snackBar.open('Product quantity increased successfully', 'Close', {
        duration: 3000
      });
      this.getCart();
    });
  }

  decreaseQuantity(productId: any) {
    this.customerService.decreaseProductQuantity(productId).subscribe(res => {
      this.snackBar.open('Product quantity decreased successfully', 'Close', {
      });
      this.getCart();
    });
  }

  placeOrder() {
    this.dialog.open(PlaceOrderComponent);
  }
}
