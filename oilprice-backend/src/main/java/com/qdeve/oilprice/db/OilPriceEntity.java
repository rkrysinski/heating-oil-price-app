/**************************************************************************
 * (C) Copyright 2016 QDEVE Roman Krysinski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **************************************************************************/
package com.qdeve.oilprice.db;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * Representation of {@link OilPrice} entity in database.
 *
 * @author Roman Krysinski
 */
@Entity
class OilPriceEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private Date date;
	private BigDecimal price;
	private String priceCurrency;
	private BigDecimal excise;
	private String exciseCurrency;
	private BigDecimal fuelSurcharge;
	private String fuelSurchargeCurrency;

	@Version
	private Integer version;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public BigDecimal getPrice()
	{
		return price;
	}

	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}

	public String getPriceCurrency()
	{
		return priceCurrency;
	}

	public void setPriceCurrency(String priceCurrency)
	{
		this.priceCurrency = priceCurrency;
	}

	public BigDecimal getExcise()
	{
		return excise;
	}

	public void setExcise(BigDecimal excise)
	{
		this.excise = excise;
	}

	public String getExciseCurrency()
	{
		return exciseCurrency;
	}

	public void setExciseCurrency(String exciseCurrency)
	{
		this.exciseCurrency = exciseCurrency;
	}

	public BigDecimal getFuelSurcharge()
	{
		return fuelSurcharge;
	}

	public void setFuelSurcharge(BigDecimal fuelSurcharge)
	{
		this.fuelSurcharge = fuelSurcharge;
	}

	public String getFuelSurchargeCurrency()
	{
		return fuelSurchargeCurrency;
	}

	public void setFuelSurchargeCurrency(String fuelSurchargeCurrency)
	{
		this.fuelSurchargeCurrency = fuelSurchargeCurrency;
	}

	public Integer getVersion()
	{
		return version;
	}

	public void setVersion(Integer version)
	{
		this.version = version;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder
	{
		private long id;
		private Date date;
		private BigDecimal price;
		private String priceCurrency;
		private BigDecimal excise;
		private String exciseCurrency;
		private BigDecimal fuelSurcharge;
		private String fuelSurchargeCurrency;

		public Builder withId(long id)
		{
			this.id = id;
			return this;
		}

		public Builder withDate(Date date)
		{
			this.date = date;
			return this;
		}

		public Builder withPrice(long price)
		{
			this.price = new BigDecimal(price);
			return this;
		}

		public Builder withPrice(BigDecimal price)
		{
			this.price = price;
			return this;
		}

		public Builder withPriceCurrency(String priceCurrency)
		{
			this.priceCurrency = priceCurrency;
			return this;
		}

		public Builder withExcise(long excise)
		{
			this.excise = new BigDecimal(excise);
			return this;
		}

		public Builder withExcise(BigDecimal excise)
		{
			this.excise = excise;
			return this;
		}

		public Builder withExciseCurrency(String exciseCurrency)
		{
			this.exciseCurrency = exciseCurrency;
			return this;
		}

		public Builder withFuelSurcharge(BigDecimal fuelSurcharge)
		{
			this.fuelSurcharge = fuelSurcharge;
			return this;
		}

		public Builder withFuelSurcharge(long fuelSurcharge)
		{
			this.fuelSurcharge = new BigDecimal(fuelSurcharge);
			return this;
		}

		public Builder withFuelSurchargeCurrency(String fuelSurchargeCurrency)
		{
			this.fuelSurchargeCurrency = fuelSurchargeCurrency;
			return this;
		}

		public OilPriceEntity build()
		{
			OilPriceEntity entity = new OilPriceEntity();
			entity.setId(id);
			entity.setDate(date);
			entity.setExcise(excise);
			entity.setExciseCurrency(exciseCurrency);
			entity.setFuelSurcharge(fuelSurcharge);
			entity.setFuelSurchargeCurrency(fuelSurchargeCurrency);
			entity.setPrice(price);
			entity.setPriceCurrency(priceCurrency);
			return entity;
		}
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((excise == null) ? 0 : excise.hashCode());
		result = prime * result + ((exciseCurrency == null) ? 0 : exciseCurrency.hashCode());
		result = prime * result + ((fuelSurcharge == null) ? 0 : fuelSurcharge.hashCode());
		result = prime * result + ((fuelSurchargeCurrency == null) ? 0 : fuelSurchargeCurrency.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((priceCurrency == null) ? 0 : priceCurrency.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		OilPriceEntity other = (OilPriceEntity) obj;
		if (date == null)
		{
			if (other.date != null)
			{
				return false;
			}
		} else if (!date.equals(other.date))
		{
			return false;
		}
		if (excise == null)
		{
			if (other.excise != null)
			{
				return false;
			}
		} else if (excise.compareTo(other.excise) != 0)
		{
			return false;
		}
		if (exciseCurrency == null)
		{
			if (other.exciseCurrency != null)
			{
				return false;
			}
		} else if (!exciseCurrency.equals(other.exciseCurrency))
		{
			return false;
		}
		if (fuelSurcharge == null)
		{
			if (other.fuelSurcharge != null)
			{
				return false;
			}
		} else if (fuelSurcharge.compareTo(other.fuelSurcharge) != 0)
		{
			return false;
		}
		if (fuelSurchargeCurrency == null)
		{
			if (other.fuelSurchargeCurrency != null)
			{
				return false;
			}
		} else if (!fuelSurchargeCurrency.equals(other.fuelSurchargeCurrency))
		{
			return false;
		}
		if (id != other.id)
		{
			return false;
		}
		if (price == null)
		{
			if (other.price != null)
			{
				return false;
			}
		} else if (price.compareTo(other.price) != 0)
		{
			return false;
		}
		if (priceCurrency == null)
		{
			if (other.priceCurrency != null)
			{
				return false;
			}
		} else if (!priceCurrency.equals(other.priceCurrency))
		{
			return false;
		}
		if (version == null)
		{
			if (other.version != null)
			{
				return false;
			}
		} else if (!version.equals(other.version))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "OilPriceEntity [id=" + id + ", date=" + date + ", price=" + price + ", priceCurrency=" + priceCurrency
				+ ", excise=" + excise + ", exciseCurrency=" + exciseCurrency + ", fuelSurcharge=" + fuelSurcharge
				+ ", fuelSurchargeCurrency=" + fuelSurchargeCurrency + ", version=" + version + "]";
	}

}
