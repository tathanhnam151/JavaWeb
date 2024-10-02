import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-post-product',
  templateUrl: './post-product.component.html',
  styleUrl: './post-product.component.scss'
})
export class PostProductComponent {

  productForm: FormGroup;
  listOfCategories: any = [];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private snackBar: MatSnackBar,
    private adminService: AdminService) { }

  ngOnInit() {
    this.productForm = this.fb.group({
      categoryId: [null, [Validators.required]],
      name: [null, [Validators.required]],
      price: [null, [Validators.required]],
      description: [null, [Validators.required]],
    });

    this.getAllCategories();
  }

  getAllCategories() {
    this.adminService.getAllCategories().subscribe((res => {
      this.listOfCategories = res;
    }));
  }

  addProduct(): void {
    if (this.productForm.valid) {
      const productData = {
        categoryId: this.productForm.get('categoryId').value,
        name: this.productForm.get('name').value,
        description: this.productForm.get('description').value,
        price: this.productForm.get('price').value
      };

      this.adminService.addProduct(productData).subscribe((res) => {
        if (res.id != null) {
          this.snackBar.open('Product added successfully', 'Close', {
            duration: 5000,
          });
          this.router.navigateByUrl('/admin/dashboard');
        } else {
          this.snackBar.open(res.message, 'ERROR', {
            duration: 5000,
          });
        }
      });
    }
  }
}
