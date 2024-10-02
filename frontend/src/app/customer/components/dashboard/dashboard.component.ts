import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  products: any = [];

  searchProductForm: FormGroup;

  constructor(private customerService: CustomerService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.getAllProducts();
    this.searchProductForm = this.fb.group({
      title: [null, [Validators.required]]
    });
  }

  getAllProducts() {
    this.products = [];
    this.customerService.getAllProducts().subscribe((res) => {
      res.forEach(element => {
        this.products.push(element);
      });
    });
  }

  submitForm() {
    this.products = [];
    const title = this.searchProductForm.get('title').value;
    this.customerService.getAllProductsByName(title).subscribe((res) => {
      res.forEach(element => {
        this.products.push(element);
      });
    });
  }

  addToCart(productId: any) {
    this.customerService.addToCart(productId).subscribe(res => {
      this.snackBar.open('Product added to cart successfully', 'Close', {
        duration: 5000
      });
    });
  }
}
